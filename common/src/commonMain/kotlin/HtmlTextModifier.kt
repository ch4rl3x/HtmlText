package de.charlex.compose.htmltext.core

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import kotlin.invoke

@Composable
fun rememberHtmlTextModifier(
    annotatedString: AnnotatedString,
    onUriClick: ((String) -> Unit)? = null,
): Pair<Modifier, MutableState<TextLayoutResult?>> {
    val clickable = annotatedString.getStringAnnotations(0, annotatedString.length - 1).any { it.tag == "url" }

    val uriHandler = LocalUriHandler.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    val urls = remember(layoutResult, annotatedString) {
        annotatedString.getStringAnnotations("url", 0, annotatedString.lastIndex)
    }

    val modifier = if (clickable) Modifier
        .pointerInput(annotatedString) {
            detectTapGestures(onTap = { pos ->
                layoutResult.value?.let { layoutResult ->
                    val position = layoutResult.getOffsetForPosition(pos)
                    annotatedString
                        .getStringAnnotations(position, position)
                        .firstOrNull()
                        ?.let { sa ->
                            if (sa.tag == "url") { // NON-NLS
                                val url = sa.item
                                onUriClick?.let { it(url) } ?: uriHandler.openUri(url)
                            }
                        }
                }
            })
        }
        .semantics {
            if (urls.size == 1) {
                role = Role.Button
                onClick("Link (${annotatedString.substring(urls[0].start, urls[0].end)}") {
                    val url = urls[0].item
                    onUriClick?.let { it(url) } ?: uriHandler.openUri(url)
                    true
                }
            } else {
                customActions = urls.map {
                    CustomAccessibilityAction("Link (${annotatedString.substring(it.start, it.end)})") {
                        val url = it.item
                        onUriClick?.let { it(url) } ?: uriHandler.openUri(url)
                        true
                    }
                }
            }
        } else Modifier

    return modifier to layoutResult
}