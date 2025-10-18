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

/**
 * HTML -> AnnotatedString converter for Compose Multiplatform
 * Supports nested <b>, <i>, <u>, <strike>, <a href=""> and now <ul><li>
 */
fun htmlToAnnotatedString(
    html: String,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
    bulletChar: String = "•", // konfigurierbar
    indentPerLevel: Int = 2 // Anzahl Spaces pro Verschachtelung
): AnnotatedString {
    val builder = Builder()

    data class TagInfo(
        val name: String,
        val style: SpanStyle,
        val start: Int,
        val href: String? = null
    )

    data class UlContext(var itemCount: Int)

    val tagStack = mutableListOf<TagInfo>()
    val ulStack = mutableListOf<UlContext>()

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
                    // rudimentäre rgb(r,g,b) Unterstützung
                    val nums = value.removePrefix("rgb(").removeSuffix(")").split(",")
                        .mapNotNull { it.trim().toIntOrNull() }
                    if (nums.size == 3) Color(nums[0], nums[1], nums[2]) else null
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun lastChar(): Char? = if (builder.length > 0) builder.toAnnotatedString().text.last() else null

    val ksoupHtmlParser = KsoupHtmlParser(
        handler = object : KsoupHtmlHandler {
            override fun onOpenTag(
                name: String,
                attributes: Map<String, String>,
                isImplied: Boolean
            ) {
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

                if (lowerName == "ul") {
                    ulStack.add(UlContext(itemCount = 0))
                }

                if (lowerName == "li") {
                    // Falls wir in einer Liste sind, Item vorbereiten
                    val currentUl = ulStack.lastOrNull()
                    val depth = ulStack.size // erste Ebene = 0
                    if (currentUl != null) {
                        val prev = lastChar()
                        // Neue Zeile vor jedem Item außer dem ersten innerhalb dieser Liste
                        if (currentUl.itemCount > 0) {
                            if (prev != '\n') builder.append('\n')
                        } else {
                            // erstes Item: nur neue Zeile wenn vorheriger Text nicht bereits Zeilenumbruch ist
                            if (prev != null && prev != '\n') builder.append('\n')
                        }
                        // Einrückung + Bullet
                        val indent = " ".repeat(depth * indentPerLevel)
                        builder.append(indent)
                        builder.append(bulletChar)
                        builder.append(" ")
                        currentUl.itemCount++
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
                if (lowerName == "ul") {
                    // Liste endet -> optional Zeilenumbruch falls nicht vorhanden und nächster Text folgt
                    // Wir fügen keinen extra Umbruch am Ende hinzu, nur wenn letzter Char nicht \n
                    // (Könnte später konfigurierbar gemacht werden)
                }
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
                if (lowerName == "ul") {
                    ulStack.removeLastOrNull()
                }
            }
        }
    )

    ksoupHtmlParser.write(html)

    ksoupHtmlParser.end()

    return builder.toAnnotatedString()
}