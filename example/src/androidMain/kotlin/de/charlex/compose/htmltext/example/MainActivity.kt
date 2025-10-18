package de.charlex.compose.htmltext.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import de.charlex.compose.htmltext.example.ui.theme.HtmlTextTheme
import de.charlex.compose.htmltext.material.HtmlText

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
                    MultipleLinks()
                    ReturnLink()
                    ReturnLinks()
                    UnsortedList()
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
fun StringGreeting() {
    HtmlText(text = "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>")
}

@Composable
fun MultipleLinks() {
    HtmlText(text = "<a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a> by <a href=\"https://github.com/ch4rl3x\">ch4rl3x</a>")
}

@Composable
fun ColorTextBySpan() {
    HtmlText(text = "Hello <span style=\"color: #0000FF\">blue</span> world")
}

@Composable
fun ColorTextByFont() {
    HtmlText(text = "Hello <font color=\"#FF0000\">red</font> world")
}

@Composable
fun UnsortedList() {
    HtmlText(text = "Unsorted list:<ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>")
}

@Composable
fun ColorTextWithColorMapping() {
    HtmlText(
        text = "Hello <font color=\"#FF0000\">red</font> world",
        colorMapping = mapOf(Color.Red to Color.Green)
    )
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

@Composable
fun ReturnLink() {
    val context = LocalContext.current
    HtmlText(
        text = "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>",
        onUriClick = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun ReturnLinks() {
    val context = LocalContext.current
    HtmlText(
        text = "<a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a> by <a href=\"https://github.com/ch4rl3x\">ch4rl3x</a>",
        onUriClick = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    )
}
