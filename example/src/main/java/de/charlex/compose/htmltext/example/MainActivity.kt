package de.charlex.compose.htmltext.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                    ColorText()
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
fun ColorText() {
    HtmlText(text = "Hello <span style=\"color: #00FFFF\">blue</span> world")
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