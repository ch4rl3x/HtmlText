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
                implementation(libs.androidx.appcompat)
                implementation("com.google.android.material:material:1.10.0")

//                implementation(compose.runtime)
//                implementation(compose.foundation)
//                implementation(compose.material)
//                implementation(compose.material3)
                implementation(compose.uiTooling)

                implementation(project(":sample-app:shared"))
            }
        }
    }
}

android {
    namespace = "de.charlex.compose.htmltext.sample"

    defaultConfig {
        applicationId = "de.charlex.compose.htmltext.sample"
        versionCode = 1
        versionName = "1.0"
    }
}
