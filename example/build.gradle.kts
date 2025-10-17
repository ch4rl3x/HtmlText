plugins {
    id("de.charlex.convention.android.application")
    id("de.charlex.convention.kotlin.multiplatform.mobile")
    id("de.charlex.convention.compose.multiplatform")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.ktx)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.uiTooling)

                implementation(project(":material-html-text"))
            }
        }
    }
}

android {
    namespace = "de.charlex.compose.cache.example"

    defaultConfig {
        applicationId = "de.charlex.compose.cache.example"
        versionCode = 1
        versionName = "1.0"
    }
}
