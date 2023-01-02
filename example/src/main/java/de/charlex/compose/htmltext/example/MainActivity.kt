package de.charlex.compose.htmltext.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.charlex.compose.HtmlText
import de.charlex.compose.htmltext.example.ui.theme.HtmlTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HtmlTextTheme {
                // A surface container using the 'background' color from the theme
                Column(Modifier.background(color = MaterialTheme.colors.background)) {
                    Greeting()
                    StringGreeting()
                    ColorTextBySpan()
                    ColorTextByFont()
                    ColorTextWithColorMapping()
                    MultipleLinks()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    HtmlText(textId = R.string.hello_world)
}

@Composable
fun StringGreeting(){
    HtmlText(text = "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>")
}

@Composable
fun MultipleLinks(){
    HtmlText(text = "<a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a> by <a href=\"https://github.com/ch4rl3x\">ch4rl3x</a>")
}

@Composable
fun ColorTextBySpan() {
    HtmlText(text = "Hello <span style=\"color: #0000FF\">blue</span> world")
}

@Composable
fun ColorTextByFont() {
    HtmlText(text = "Hello <font color=\"red\">red</font> world")
}

@Composable
fun ColorTextWithColorMapping() {
    HtmlTextTheme(
        darkTheme = false
    ) {
        Surface {
            HtmlText(
                text = "Hello <font color=\"red\">red</font> world",
                colorMapping = mapOf(Color.Red to MaterialTheme.colors.primary)
            )
        }
    }

    HtmlTextTheme(
        darkTheme = true
    ) {
        Surface {
            HtmlText(
                text = "Hello <font color=\"red\">red</font> world",
                colorMapping = mapOf(Color.Red to MaterialTheme.colors.primary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HtmlTextTheme {
        Column {
            Greeting()
            StringGreeting()
        }
    }
}