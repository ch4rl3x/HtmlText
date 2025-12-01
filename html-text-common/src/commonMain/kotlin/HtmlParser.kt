package de.charlex.compose.htmltext.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.Builder
import androidx.compose.ui.text.AnnotatedString.Builder.BulletScope
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser
import kotlin.compareTo
import kotlin.text.append


fun htmlToAnnotatedString(
    html: String,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
    bulletChar: String = "•",
    indentPerLevel: TextUnit = 15.sp,
    extraIndentUnorderedRestLines: TextUnit = 8.sp,
    extraIndentOrderedRestLines: TextUnit = 15.sp,
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
        val type: Char? = null // a, A, 1
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

    fun formatOrdered(index: Int, type: Char?): String = when (type) {
        'a' -> ('a' + (index - 1) % 26).toString()
        'A' -> ('A' + (index - 1) % 26).toString()
        else -> index.toString()
    }

    var possibleNextLineBreakInList by mutableStateOf(false)
    var closedSubList by mutableStateOf(false)

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
                val rawColor = attributes["color"] ?: attributes["style"]?.substringAfter("color:", "").orEmpty().substringBefore(";").ifBlank { null }
                val parsedColor = parseColorAttr(rawColor)
                if (parsedColor != null) {
                    val mapped = colorMapping.getOrElse(parsedColor) { parsedColor }
                    style = style.merge(SpanStyle(color = mapped))
                }

                if (lowerName == "ul" || lowerName == "ol") {
                    val ordered = lowerName == "ol"
                    val startIndex = attributes["start"]?.toIntOrNull()?.coerceAtLeast(1) ?: 1
                    val typeAttr = attributes["type"]?.firstOrNull()?.let { ch ->
                        if (ch in listOf('a', 'A', '1')) ch else null
                    }
                    listStack.add(ListContext(ordered = ordered, itemCount = 0, startIndex = startIndex, type = typeAttr))

                    val depth = listStack.size

                    val restLineIndent = when {
                        ordered -> ((indentPerLevel * depth).value + extraIndentOrderedRestLines.value).sp
                        else -> ((indentPerLevel * depth).value + extraIndentUnorderedRestLines.value).sp
                    }

                    builder.pushStyle(
                        ParagraphStyle(
                            textIndent = TextIndent(
                                firstLine = indentPerLevel * depth,
                                restLine = restLineIndent
                            )
                        )
                    )
                }

                if (lowerName == "li") {
                    val currentList = listStack.lastOrNull()
                    if (currentList != null) {
                        if(possibleNextLineBreakInList) {
                            println(builder.toAnnotatedString().text)
                            builder.append('\n')
                            possibleNextLineBreakInList = false
                        }

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

                if(lowerName == "li") {
                    val currentList = listStack.lastOrNull()
                    if (currentList != null && currentList.itemCount > 0 && closedSubList.not()) {
                        possibleNextLineBreakInList = true
                    } else {
                        closedSubList = false
                    }

                }

                if (lowerName == "ul" || lowerName == "ol") {
                    listStack.removeLastOrNull()
                    closedSubList = listStack.isNotEmpty()
                    possibleNextLineBreakInList = false
                    builder.pop()
                }
            }
        }
    )

    ksoupHtmlParser.write(html)
    ksoupHtmlParser.end()
    return builder.toAnnotatedString()
}
