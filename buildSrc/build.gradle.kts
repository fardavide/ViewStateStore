plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    val android = "3.4.1"
    val dokka = "0.9.18"
    val bintray = "1.8.4"

    implementation("com.android.tools.build:gradle:$android" )
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:$dokka" )
    implementation("org.jetbrains.dokka:dokka-android-gradle-plugin:$dokka" )
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintray" )
}