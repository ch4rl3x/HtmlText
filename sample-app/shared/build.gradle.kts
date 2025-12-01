import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import de.charlex.convention.config.configureIosTargets

plugins {
    id("de.charlex.convention.android.library")
    id("de.charlex.convention.kotlin.multiplatform.mobile")
    id("de.charlex.convention.compose.multiplatform")
}

//android {
//    namespace = "de.charlex.convention.compose.sample.shared"
//}

kotlin {
    configureIosTargets()
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.components.resources)
                implementation(compose.material)
                implementation(compose.material3)

                implementation(projects.htmlTextMaterial)
                implementation(projects.htmlTextMaterial3)
            }
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.withType<Framework> {
            isStatic = true
            baseName = "shared"
//            export("io.github.kalinjul.kotlin.multiplatform:oidc-appsupport")
        }
    }
}
