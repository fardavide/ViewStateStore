plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    val android =       "3.6.1"         // Updated: Feb 28, 2020
    val easyGradle =    "1.2.3-beta-3"  // Updated: Jan 04, 2020

    implementation("com.android.tools.build:gradle:$android")
    implementation("studio.forface.easygradle:dsl-android:$easyGradle")
}
