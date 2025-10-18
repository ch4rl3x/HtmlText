package de.charlex.compose.htmltext.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainView() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = androidx.compose.material.MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        verticalArrangement = spacedBy(20.dp),
    ) {
        var material3 by remember { mutableStateOf(true) }

        androidx.compose.material3.MaterialTheme {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = spacedBy(20.dp)
            ) {
                Switch(
                    checked = material3,
                    onCheckedChange = {
                        material3 = it
                    }
                )
                Text("Material 3")
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = spacedBy(10.dp),
        ) {
            if(material3) {
                androidx.compose.material3.MaterialTheme {
                    CompositionLocalProvider(androidx.compose.material3.LocalTextStyle provides TextStyle(fontSize = 14.sp)) {
                        SharedMainView(
                            stringResourceHtmlText = { stringRes ->
                                de.charlex.compose.htmltext.material3.HtmlText(
                                    stringResource = stringRes
                                )
                            },
                            textHtmlText = { text, colorMapping, onUriClick ->
                                de.charlex.compose.htmltext.material3.HtmlText(
                                    text = text,
                                    colorMapping = colorMapping,
                                    onUriClick = onUriClick
                                )
                            }
                        )
                    }
                }
            } else {
                androidx.compose.material.MaterialTheme {
                    CompositionLocalProvider(androidx.compose.material.LocalTextStyle provides TextStyle(fontSize = 14.sp)) {
                        SharedMainView(
                            stringResourceHtmlText = { stringRes ->
                                de.charlex.compose.htmltext.material.HtmlText(
                                    stringResource = stringRes
                                )
                            },
                            textHtmlText = { text, colorMapping, onUriClick ->
                                de.charlex.compose.htmltext.material.HtmlText(
                                    text = text,
                                    colorMapping = colorMapping,
                                    onUriClick = onUriClick
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
