package de.charlex.compose.htmltext.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.charlex.compose.htmltext.example.ui.theme.HtmlTextTheme
import de.charlex.compose.htmltext.material.HtmlText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HtmlTextTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .background(color = MaterialTheme.colors.background)
                        .padding(horizontal = 10.dp),
                    verticalArrangement = spacedBy(12.dp)
                ) {
                    Greeting()
                    StringGreeting()
                    ColorTextBySpan()
                    ColorTextByFont()
                    ColorTextWithColorMapping()
                    MultipleLinks()
                    ReturnLink()
                    ReturnLinks()
                    UnorderedList()

                    FlowRow {
                        OrderedList("a")
                        OrderedList("A")
                        OrderedList("i")
                        OrderedList("I")
                        OrderedList("1")
                    }

                    NestedLists()
                    OrderedListVariants()
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
    HtmlText(text = "<span style=\"color: #0000FF\">Blue</span> span color")
}

@Composable
fun ColorTextByFont() {
    HtmlText(text = "<font color=\"#FF0000\">Red</font> font color")
}
@Composable
fun ColorTextWithColorMapping() {
    HtmlText(
        text = "Replace red color in content with green: <font color=\"#FF0000\">content</font>",
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

@Composable
fun UnorderedList() {
    HtmlText(text = "Unordered<ul><li>Item</li><li>Item</li><li>Item</li></ul>")
}

@Composable
fun OrderedList(type: String) {
    HtmlText(text = "Ordered<ol type=\"$type\"><li>Item</li><li>Item</li><li>Item</li><li>Item</li><li>Item</li></ol>")
}

@Composable
fun NestedLists() {
    HtmlText(text = "Nested<ul><li>Parent<ul><li>Child</li><li>Child</li></ul></li><li>Parent</li></ul>")
}

@Composable
fun OrderedListVariants() {
    HtmlText(text = "Ordered variants<ol start=\"3\" type=\"1\"><li>Item</li><li>Item<ul><li>Sub</li><li>Sub</li></ul></li><li>Item</li></ol>")
}
