package de.charlex.compose.htmltext.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.charlex.compose.HtmlText
import de.charlex.compose.htmltext.example.ui.theme.HtmlTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HtmlTextTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    HtmlText(textId = R.string.hello_world)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HtmlTextTheme {
        Greeting()
    }
}