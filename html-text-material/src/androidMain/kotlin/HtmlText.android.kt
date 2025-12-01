package de.charlex.compose.htmltext.material

import androidx.annotation.StringRes
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.unit.sp
import de.charlex.compose.htmltext.core.htmlToAnnotatedString

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
 * @see Text
 *
 */
@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    linkBoxModifier: (text: String, link: String) -> Modifier = { _, _ -> Modifier },
    @StringRes cdataStringId: Int,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colors.secondary,
        textDecoration = TextDecoration.Underline
    ),
    colorMapping: Map<Color, Color> = emptyMap(),
    bulletChar: String = "•",
    indentPerLevel: TextUnit = 15.sp,
    extraIndentUnorderedRestLines: TextUnit = 8.sp,
    extraIndentOrderedRestLines: TextUnit = 15.sp,
    orderedSeparator: String = ".",
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
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onUriClick: ((String) -> Unit)? = null,
) {
    val annotatedString = htmlToAnnotatedString(
        html = stringResource(cdataStringId),
        urlSpanStyle = urlSpanStyle,
        colorMapping = colorMapping,
        bulletChar = bulletChar,
        indentPerLevel = indentPerLevel,
        extraIndentUnorderedRestLines = extraIndentUnorderedRestLines,
        extraIndentOrderedRestLines = extraIndentOrderedRestLines,
        orderedSeparator = orderedSeparator
    )

    HtmlText(
        modifier = modifier,
        linkBoxModifier = linkBoxModifier,
        annotatedString = annotatedString,
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
        minLines = minLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style,
        onUriClick = onUriClick
    )
}
