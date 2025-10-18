package de.charlex.compose.htmltext.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp

@Composable
fun BaseHtmlText(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    linkBoxModifier: (text: String, link: String) -> Modifier = { _, _ -> Modifier },
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onUriClick: ((String) -> Unit)? = null,
    text: @Composable (modifier: Modifier, onTextLayout: (TextLayoutResult) -> Unit) -> Unit
) {
    val urlAnns = remember(annotatedString) {
        annotatedString.getStringAnnotations("url", 0, annotatedString.length)
    }
    val layoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val density = LocalDensity.current
    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier.semantics(mergeDescendants = isIOS().not()) {
            contentDescription = annotatedString.text
        }
    ) {
        text(
            modifier
            .semantics {
                if(isIOS().not()) {
                    hideFromAccessibility()
                }
            }
        ) {
            layoutResultState.value = it
            onTextLayout(it)
        }

        val layoutResult = layoutResultState.value
        if (layoutResult != null) {
            urlAnns.forEachIndexed { index, ann ->
                val rects = remember(layoutResult, ann) {
                    computeLinkRects(layoutResult, ann.start, ann.end)
                }
                rects.forEach { r ->
                    Box(
                        modifier = linkBoxModifier(annotatedString.text.substring(ann.start, ann.end), ann.item)
                            .offset(
                                x = with(density) { r.left.toDp().takeIf { it > 0.dp } ?: 1.dp }, // If we move the first clickable box to x = 1.dp, the screenreader will read the while string first
                                y = with(density) { r.top.toDp() }
                            )
                            .size(
                                width = with(density) { r.width.toDp() },
                                height = with(density) { r.height.toDp() }
                            )
                            .semantics {
                                contentDescription = "Link: ${annotatedString.text.substring(ann.start, ann.end)}"
                            }
                            .clickable {
                                onUriClick?.let { it(ann.item) } ?: uriHandler.openUri(ann.item)
                            }
                    )
                }
            }
        }
    }
}