# HtmlText
## Current Compose Version: 1.0.3
Compose HtmlText

Text composable to show html text from resources

<a href="https://github.com/ch4rl3x/HtmlText/actions?query=workflow%3ALint"><img src="https://github.com/ch4rl3x/HtmlText/workflows/Lint/badge.svg" alt="Lint"></a>
<a href="https://github.com/ch4rl3x/HtmlText/actions?query=workflow%3AKtlint"><img src="https://github.com/ch4rl3x/HtmlText/workflows/Ktlint/badge.svg" alt="Ktlint"></a>

<a href="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText"><img src="https://www.codefactor.io/repository/github/ch4rl3x/HtmlText/badge" alt="CodeFactor" /></a>
<a href="https://repo1.maven.org/maven2/de/charlex/compose/html-text/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose/html-text" alt="Maven Central" /></a>


# Add to your project

Add actual HtmlText library:

```groovy
dependencies {
    implementation 'de.charlex.compose:html-text:1.0.0'
}
```

# How does it work?

Use it like a normal Text composable

```kotlin
HtmlText(textId = R.string.hello_world)
```

```xml
<resources>
    <string name="hello_world">Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href="https://github.com/ch4rl3x/HtmlText">HtmlText</a></string>
</resources>
```

# Preview

![HtmlText](https://github.com/ch4rl3x/HtmlText/blob/main/art/screenshot.png)


That's it!

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
