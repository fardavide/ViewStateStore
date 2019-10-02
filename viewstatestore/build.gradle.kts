import studio.forface.easygradle.dsl.`kotlin-jdk7`
import studio.forface.easygradle.dsl.android.`lifecycle-liveData`
import studio.forface.easygradle.dsl.android.dokkaAndroid
import studio.forface.easygradle.dsl.api
import studio.forface.easygradle.dsl.publish

plugins {
    `android-library`
    id("kotlin-android") // TODO: Unresolved ref :/ - `kotlin-android`
}

android { applyAndroidConfig() }

dependencies {
    applyAndroidTests()

    api(
        `kotlin-jdk7`,
        `lifecycle-liveData`
    )
}

dokkaAndroid()
publish(baseBlock = defaultPublishConfig) {
    projectName = "viewstatestore"
    artifact = "viewstatestore"
}
