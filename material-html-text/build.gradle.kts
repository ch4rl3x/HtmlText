import de.charlex.convention.config.configureIosTargets

plugins {
    id("de.charlex.convention.android.library")
    id("de.charlex.convention.kotlin.multiplatform.mobile")
    id("de.charlex.convention.centralPublish")
    id("de.charlex.convention.compose.multiplatform")
}

mavenPublishConfig {
    name = "material-html-text"
    description = "A Kotlin Multiplatform library to render HTML content as Compose AnnotatedString in Material, supporting basic formatting and hyperlinks."
    url = "https://github.com/ch4rl3x/HtmlText"

    scm {
        connection = "scm:git:github.com/ch4rl3x/HtmlText.git"
        developerConnection = "scm:git:ssh://github.com/ch4rl3x/HtmlText.git"
        url = "https://github.com/ch4rl3x/HtmlText/tree/main"
    }

    developers {
        developer {
            id = "ch4rl3x"
            name = "Alexander Karkossa"
            email = "alexander.karkossa@googlemail.com"
        }
        developer {
            id = "kalinjul"
            name = "Julian Kalinowski"
            email = "julakali@gmail.com"
        }
    }
}


kotlin {
    configureIosTargets()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.components.resources)

                implementation(project(":common"))
            }
        }
    }
}