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
 * Supports nested <b>, <i>, <u>, <strike>, <a href="">
 */
fun htmlToAnnotatedString(
    html: String,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap()
): AnnotatedString {
    val builder = Builder()

    data class TagInfo(
        val name: String,
        val style: SpanStyle,
        val start: Int,
        val href: String? = null
    )

    val tagStack = mutableListOf<TagInfo>()

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
            }
        }
    )

    ksoupHtmlParser.write(html)

    ksoupHtmlParser.end()

    return builder.toAnnotatedString()
}