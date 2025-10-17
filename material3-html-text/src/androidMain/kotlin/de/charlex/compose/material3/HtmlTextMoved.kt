package de.charlex.compose.material3

import androidx.annotation.StringRes
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import de.charlex.compose.htmltext.core.htmlToAnnotatedString
import de.charlex.compose.htmltext.material3.HtmlText


/**
 * Simple Text composable to show the text with html styling from string resources.
 * Supported are:
 *
 * &lt;b>Bold&lt;/b>
 *
 * &lt;i>Italic&lt;/i>
 *
 * &lt;u>Underlined&lt;/u>
 *
 * &lt;strike>Strikethrough&lt;/strike>
 *
 * &lt;a href="https://google.de">Link&lt;/a>
 *
 * @see androidx.compose.material.Text
 *
 */
@Composable
@Deprecated(
    message = "Moved to de.charlex.compose.htmltext.material3 package",
    replaceWith = ReplaceWith("HtmlText", "de.charlex.compose.htmltext.material3.HtmlText"),
    level = DeprecationLevel.ERROR
)
fun HtmlText(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.tertiary,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onUriClick: ((String) -> Unit)? = null,
) {
    HtmlText(
        modifier = modifier,
        textId = textId,
        urlSpanStyle = urlSpanStyle,
        colorMapping = colorMapping,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style,
        onUriClick = onUriClick
    )
}
