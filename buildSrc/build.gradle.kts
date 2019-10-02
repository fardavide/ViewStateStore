plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    val android =       "3.6.0-alpha12" // Updated: Sep 18, 2019
    val easyGradle =    "0.21"          // Updated: Oct 02, 2019

    implementation("com.android.tools.build:gradle:$android")
    implementation("studio.forface.easygradle:dsl-android:$easyGradle")
}
