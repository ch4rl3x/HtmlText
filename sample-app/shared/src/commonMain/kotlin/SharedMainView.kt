package de.charlex.compose.htmltext.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.charlex.compose.shared.generated.resources.Res
import de.charlex.compose.shared.generated.resources.hello_world_cdata
import de.charlex.compose.shared.generated.resources.hello_world_excaped
import org.jetbrains.compose.resources.StringResource

@Composable
fun SharedMainView(
    stringResourceHtmlText: @Composable (StringResource) -> Unit,
    textHtmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit
) {
    ResourcesEscaped(stringResourceHtmlText)
    ResourcesCDATA(stringResourceHtmlText)
    StringGreeting(textHtmlText)
    ColorTextBySpan(textHtmlText)
    ColorTextByFont(textHtmlText)
    ColorTextWithColorMapping(textHtmlText)
    MultipleLinks(textHtmlText)
    ReturnLink(textHtmlText)
    ReturnLinks(textHtmlText)
    UnorderedList(textHtmlText)

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OrderedList("a", textHtmlText)
        OrderedList("A", textHtmlText)
        OrderedList("1", textHtmlText)
    }

    NestedLists(textHtmlText)
    OrderedListVariants(textHtmlText)
    UnorderedListVariants2(textHtmlText)
}


@Composable
private fun ResourcesEscaped(htmlText: @Composable (StringResource) -> Unit) {
    htmlText(Res.string.hello_world_excaped)
}

@Composable
private fun ResourcesCDATA(htmlText: @Composable (StringResource) -> Unit) {
    htmlText(Res.string.hello_world_cdata)
}

@Composable
private fun StringGreeting(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>", emptyMap(), null)
}

@Composable
private fun MultipleLinks(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a> by <a href=\"https://github.com/ch4rl3x\">ch4rl3x</a>", emptyMap(), null)
}

@Composable
private fun ColorTextBySpan(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<span style=\"color: #0000FF\">Blue</span> span color", emptyMap(), null)
}

@Composable
private fun ColorTextByFont(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<font color=\"#FF0000\">Red</font> font color", emptyMap(), null)
}
@Composable
private fun ColorTextWithColorMapping(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText(
        "Replace red color in content with green: <font color=\"#FF0000\">content</font>",
        mapOf(Color.Red to Color.Green),
        null
    )
}



@Composable
private fun ReturnLink(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText(
        "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>",
        emptyMap(),
        { }
    )
}

@Composable
private fun ReturnLinks(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText(
        "<a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a> by <a href=\"https://github.com/ch4rl3x\">ch4rl3x</a>",
        emptyMap(),
        { }
    )
}

@Composable
private fun UnorderedList(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<b>Unordered</b><ul><li>Item</li><li>Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item</li><li>Item</li></ul>", emptyMap(), null)
}

@Composable
private fun OrderedList(type: String, htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<b>Ordered</b><ol type=\"$type\"><li>Item</li><li>Item</li><li>Item</li><li>Item</li><li>Item</li></ol>", emptyMap(), null)
}

@Composable
private fun NestedLists(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<b>Nested</b><ul><li>Parent<ul><li>Child</li><li>Child</li></ul></li><li>Parent</li></ul>", emptyMap(), null)
}

@Composable
private fun OrderedListVariants(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<b>Ordered variants</b><ol start=\"3\" type=\"1\"><li>Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item </li><li>Item<ul><li>Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub </li><li>Sub</li></ul></li><li>Item</li><li>Item</li><li>Item<ol><li>Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub </li><li>Sub</li></ol></li><li>Item</li></ol>", emptyMap(), null)
}

@Composable
private fun UnorderedListVariants2(htmlText: @Composable (String, Map<Color, Color>, ((String) -> Unit)?) -> Unit) {
    htmlText("<b>Unordered variants</b><ul><li>Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item Item </li><li>Item<ul><li>Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub </li><li>Sub</li></ul></li><li>Item</li><li>Item</li><li>Item<ol><li>Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub Sub </li><li>Sub</li></ol></li><li>Item</li></ul>", emptyMap(), null)
}
