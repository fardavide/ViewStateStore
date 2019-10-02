@file:Suppress("MayBeConstant", "ConstantConditionIf")

import org.gradle.api.JavaVersion
import studio.forface.easygradle.dsl.android.Version
import studio.forface.easygradle.dsl.android.Version.Channel.Beta
import studio.forface.easygradle.dsl.android.androidVersion
import studio.forface.easygradle.dsl.publishConfig

/**
 * @author Davide Giuseppe Farella.
 * An object containing params for the Library.
 */
object Project {

    /* Version */
    val version = Version(
        major =     1,
        minor =     3,
        channel =   Beta,
        patch =     2,
        build =     1
    )

    /** The Android API level as target of the App */
    val targetSdk = 28
    /** The Android API level required for run the App */
    val minSdk = 14
    /** The version of the JDK  */
    val jdkVersion = JavaVersion.VERSION_1_8
}

val defaultPublishConfig = publishConfig {
    androidVersion = Project.version
}
