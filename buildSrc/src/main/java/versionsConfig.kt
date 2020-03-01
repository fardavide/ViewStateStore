import studio.forface.easygradle.dsl.`coroutines version`
import studio.forface.easygradle.dsl.`kotlin version`
import studio.forface.easygradle.dsl.`mockK version`
import studio.forface.easygradle.dsl.android.`android-gradle-plugin version`
import studio.forface.easygradle.dsl.android.`android-test version`
import studio.forface.easygradle.dsl.android.`lifecycle version`

fun initVersions() {

    // region Kotlin
    `kotlin version` =                  "1.3.61"        // Updated: Nov 26, 2019
    `coroutines version` =              "1.3.3"         // Updated: Dec 13, 2019
    `mockK version` =                   "1.9"
    // endregion

    // region Android
    `android-gradle-plugin version` =   "3.6.1"         // Updated: Feb 28, 2020
    `lifecycle version` =               "2.2.0"         // Updated: Jan 23, 2020
    `android-test version` =            "1.1.1"
    // endregion
}
