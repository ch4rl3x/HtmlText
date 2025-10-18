package de.charlex.compose.htmltext.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.Builder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser


fun htmlToAnnotatedString(
    html: String,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
    bulletChar: String = "•",
    indentPerLevel: Int = 2,
    orderedSeparator: String = "."
): AnnotatedString {
    val builder = Builder()

    data class TagInfo(
        val name: String,
        val style: SpanStyle,
        val start: Int,
        val href: String? = null
    )

    data class ListContext(
        val ordered: Boolean,
        var itemCount: Int,
        val startIndex: Int = 1,
        val type: Char? = null // a, A, i, I, 1
    )

    val tagStack = mutableListOf<TagInfo>()
    val listStack = mutableListOf<ListContext>()

    fun parseColorAttr(raw: String?): Color? {
        if (raw == null) return null
        val value = raw.trim()
        return try {
            when {
                value.startsWith("#") -> {
                    val parsed = value.removePrefix("#")
                    val colorLong = parsed.toLong(16)
                    when (parsed.length) {
                        6 -> Color(colorLong or 0xFF000000)
                        8 -> Color(colorLong)
                        else -> null
                    }
                }
                value.startsWith("rgb") -> {
                    val nums = value.removePrefix("rgb(").removeSuffix(")").split(",")
                        .mapNotNull { it.trim().toIntOrNull() }
                    if (nums.size == 3) Color(nums[0], nums[1], nums[2]) else null
                }
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }

    fun lastChar(): Char? = if (builder.length > 0) builder.toAnnotatedString().text.last() else null

    fun toRoman(num: Int): String { // einfache Umsetzung bis 3999
        if (num <= 0) return num.toString()
        val numerals = listOf(
            1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
            100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
            10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
        )
        var n = num
        val sb = StringBuilder()
        for ((value, symbol) in numerals) {
            while (n >= value) {
                sb.append(symbol)
                n -= value
            }
        }
        return sb.toString()
    }

    fun formatOrdered(index: Int, type: Char?): String = when (type) {
        'a' -> ('a' + (index - 1) % 26).toString()
        'A' -> ('A' + (index - 1) % 26).toString()
        'i' -> toRoman(index).lowercase()
        'I' -> toRoman(index)
        else -> index.toString()
    }

    val ksoupHtmlParser = KsoupHtmlParser(
        handler = object : KsoupHtmlHandler {
            override fun onOpenTag(name: String, attributes: Map<String, String>, isImplied: Boolean) {
                val lowerName = name.lowercase()
                val start = builder.length

                var style = when (lowerName) {
                    "b", "strong" -> SpanStyle(fontWeight = FontWeight.Bold)
                    "i", "em" -> SpanStyle(fontStyle = FontStyle.Italic)
                    "u" -> SpanStyle(textDecoration = TextDecoration.Underline)
                    "strike", "s" -> SpanStyle(textDecoration = TextDecoration.LineThrough)
                    "a" -> urlSpanStyle
                    else -> SpanStyle()
                }

                // Farbe aus Attribut oder Style-Attribut extrahieren
                val rawColor = attributes["color"]
                    ?: attributes["style"]?.substringAfter("color:", "").orEmpty()
                        .substringBefore(";").ifBlank { null }
                val parsedColor = parseColorAttr(rawColor)
                if (parsedColor != null) {
                    val mapped = colorMapping.getOrElse(parsedColor) { parsedColor }
                    style = style.merge(SpanStyle(color = mapped))
                }

                if (lowerName == "ul" || lowerName == "ol") {
                    val ordered = lowerName == "ol"
                    val startIndex = attributes["start"]?.toIntOrNull()?.coerceAtLeast(1) ?: 1
                    val typeAttr = attributes["type"]?.firstOrNull()?.let { ch ->
                        if (ch in listOf('a', 'A', 'i', 'I', '1')) ch else null
                    }
                    listStack.add(ListContext(ordered = ordered, itemCount = 0, startIndex = startIndex, type = typeAttr))
                }

                if (lowerName == "li") {
                    val currentList = listStack.lastOrNull()
                    val depth = listStack.size
                    if (currentList != null) {
                        val prev = lastChar()
                        if (currentList.itemCount > 0) {
                            if (prev != '\n') builder.append('\n')
                        } else {
                            if (prev != null && prev != '\n') builder.append('\n')
                        }
                        val indent = " ".repeat(depth * indentPerLevel)
                        builder.append(indent)
                        if (currentList.ordered) {
                            val numberIndex = currentList.startIndex + currentList.itemCount
                            val formatted = formatOrdered(numberIndex, currentList.type)
                            builder.append(formatted)
                            builder.append(orderedSeparator)
                            builder.append(" ")
                        } else {
                            builder.append(bulletChar)
                            builder.append(" ")
                        }
                        currentList.itemCount++
                    }
                }

                val href = if (lowerName == "a") attributes["href"] else null
                tagStack.add(TagInfo(lowerName, style, start, href))
            }

            override fun onText(text: String) {
                builder.append(text)
            }

            override fun onCloseTag(name: String, isImplied: Boolean) {
                val lowerName = name.lowercase()
                val idx = tagStack.indexOfLast { it.name == lowerName }
                if (idx != -1) {
                    val tag = tagStack.removeAt(idx)
                    val end = builder.length
                    if (end > tag.start) {
                        builder.addStyle(tag.style, tag.start, end)
                        if (tag.href != null) {
                            builder.addStringAnnotation(
                                tag = "url",
                                annotation = tag.href,
                                start = tag.start,
                                end = end
                            )
                        }
                    }
                }
                if (lowerName == "ul" || lowerName == "ol") {
                    listStack.removeLastOrNull()
                }
            }
        }
    )

    ksoupHtmlParser.write(html)

    ksoupHtmlParser.end()

    return builder.toAnnotatedString()
}