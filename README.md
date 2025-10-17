<a href="https://github.com/ch4rl3x/HtmlText/actions?query=workflow%3ABuild"><img src="https://github.com/ch4rl3x/HtmlText/workflows/build.yml/badge.svg" alt="Build"></a>
<a href="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText"><img src="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText/badge" alt="CodeFactor" /></a>

Material <a href="https://repo1.maven.org/maven2/de/charlex/compose/material/material-html-text/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose.material/material-html-text" alt="Maven Central" /></a>

Material 3 <a href="https://repo1.maven.org/maven2/de/charlex/compose/material3/material3-html-text/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose.material3/material3-html-text" alt="Maven Central" /></a>

# HtmlText

`HtmlText` is a Kotlin Multiplatform library that allows you to render HTML content as Compose `AnnotatedStrings. It supports basic formatting, hyperlinks, and color styling in a multiplatform-friendly way.

> [!NOTE]  
> 🚀 RevealSwipe is now Compose Multiplatform

## Supported HTML tags

| Tag | Description |
|-----|------------|
| `<b>` | Bold text |
| `<i>` | Italic text |
| `<strike>` | Strikethrough text |
| `<u>` | Underlined text |
| `<a href="...">` | Clickable link |
| `<span style="color: #0000FF">` | Colored text |
| `<span style="color: red">` | Colored text |
| `<font color="#FF0000">` | Colored text |
| `<font color="red">` | Colored text |


## MaterialTheme colors in HtmlText
To use colors like `MaterialTheme.colors.primary` in `HtmlText`, map simple colors.
```kotlin
HtmlText(
    textId = R.string.hello_world,
    colorMapping = mapOf(Color.Red to MaterialTheme.colors.primary)
)
```
```xml
<resources>
    <string name="hello_world">"Hello <span style="color: red">World</span>"</string>
</resources>
```

## String arguments in HtmlText
To use string arguments with `HtmlText`, use CDATA
```kotlin
HtmlText(
    text = stringResource(R.string.hello_world, "Hello")
)
```
```xml
<resources>
    <string name="hello_world"><![CDATA[%1$s World]]></string>
</resources>
```

## Combine MaterialTheme colors and string arguments in HtmlText
You can combine colorMapping with string arguments (CDATA) and all other HTML tags. If you use CDATA, you have to escape double quotes.
```xml
<resources>
    <string name="hello_world"><![CDATA[Hello <span style=\"color: red\">World</span>]]></string>
</resources>
```



## Dependency

Add actual HtmlText library:

```groovy
dependencies {
    implementation 'de.charlex.compose.material:material-html-text:2.0.0-beta01'
}
```

or

```groovy
dependencies {
    implementation 'de.charlex.compose.material3:material3-html-text:2.0.0-beta01'
}
```

## How does it work?

Use it like a normal Text composable

```kotlin
HtmlText(textId = R.string.hello_world)
```

```xml
<resources>
    <string name="hello_world">Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href="https://github.com/ch4rl3x/HtmlText">HtmlText</a></string>
</resources>
```

## Preview

![HtmlText](https://github.com/ch4rl3x/HtmlText/blob/main/art/screenshot.png)


License
--------

    Copyright 2021 Alexander Karkossa

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
