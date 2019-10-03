import studio.forface.easygradle.dsl.android.*
import studio.forface.easygradle.dsl.api
import studio.forface.easygradle.dsl.implementation

plugins {
    `android-library`
    id("kotlin-android") // TODO: Unresolved ref :/ - `kotlin-android`
    id("kotlin-android-extensions")// TODO: Unresolved ref :/ - `kotlin-android-extensions`
}

android { applyAndroidConfig() }

dependencies {
    implementation(project(":viewstatestore"))
    applyAndroidTests()

    api(`paging-runtime`)
    androidTestImplementation(`paging-common`)
}

dokkaAndroid()
publishAndroid(defaultPublishConfig) {
    projectName = "paging"
    artifact = "viewstatestore-paging"
}
