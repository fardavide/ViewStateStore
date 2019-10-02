import studio.forface.easygradle.dsl.`kotlin version`
import studio.forface.easygradle.dsl.`mockK version`
import studio.forface.easygradle.dsl.android.`android-gradle-plugin version`
import studio.forface.easygradle.dsl.android.`android-test version`
import studio.forface.easygradle.dsl.android.`espresso version`
import studio.forface.easygradle.dsl.android.`lifecycle version`

fun initVersions() {

    // region Kotlin
    `kotlin version` =                  "1.3.50"        // Updated: Aug 22, 2019
    `mockK version` =                   "1.9"
    // endregion

    // region Android
    `android-gradle-plugin version` =   "3.6.0-alpha12" // Updated: Sep 18, 2019
    `lifecycle version` =               "2.2.0-alpha05" // Updated: Sep 18, 2019
    `espresso version` =                "3.1.1"
    `android-test version` =            "1.1.1"
    // endregion
}
