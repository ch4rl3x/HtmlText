<a href="https://github.com/ch4rl3x/HtmlText/actions?query=workflow%3ABuild"><img src="https://github.com/ch4rl3x/HtmlText/actions/workflows/build.yml/badge.svg" alt="Build"></a>
<a href="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText"><img src="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText/badge" alt="CodeFactor" /></a>
<a href="https://repo1.maven.org/maven2/de/charlex/compose/material3/material3-html-text/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose.material3/material3-html-text" alt="Maven Central" /></a> 


# HtmlText

`HtmlText` is a Kotlin Multiplatform library that allows you to render HTML content as Compose `AnnotatedStrings. It supports basic formatting, hyperlinks, and color styling in a multiplatform-friendly way.

> [!NOTE]  
> 🚀 HtmlText is now Compose Multiplatform

## Supported HTML tags

| Tag                                | Description               |
|------------------------------------|---------------------------|
| `<b>`                              | Bold text                 |
| `<i>`                              | Italic text               |
| `<strike>`                         | Strikethrough text        |
| `<u>`                              | Underlined text           |
| `<ul>`                             | Unordered list            |
| `<ol start="3" type="1">`          | Ordered list (a., A., 1.) |
| `<li>`                             | List item                 |
| `<a href="...">`                   | Clickable link            |
| `<span style="color: #0000FF">`    | Colored text              |
| `<span style="color: rgb(r,g,b)">` | Colored text              |
| `<font color="#FF0000">`           | Colored text              |
| `<font color="rgb(r,g,b)">`        | Colored text              |


## MaterialTheme colors in HtmlText
To use colors like `MaterialTheme.colors.primary` in `HtmlText`, map simple colors.
```kotlin
HtmlText(
    stringId = R.string.hello_world_cdata,
    colorMapping = mapOf(Color.Red to MaterialTheme.colors.primary)
)
```
```xml
<resources>
    <string name="hello_world_escaped">Hello &lt;span style="color: #FF0000"&gt;World&lt;/span&gt;</string>
    <string name="hello_world_cdata"><![CDATA[Hello <span style="color: #FF0000">World</span>]]></string>
</resources>
```

## Preview

![HtmlText](https://github.com/ch4rl3x/HtmlText/blob/main/art/screenshot.png)

## Dependency

Add actual HtmlText library:

```groovy
dependencies {
    implementation 'de.charlex.compose.material:material-html-text:3.0.0-beta01'
}
```

or

```groovy
dependencies {
    implementation 'de.charlex.compose.material3:material3-html-text:3.0.0-beta01'
}
```

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
