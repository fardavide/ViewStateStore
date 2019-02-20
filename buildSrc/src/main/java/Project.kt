@file:Suppress("MayBeConstant")

import Project.Channel.Build
import Project.Channel.Stable
import org.gradle.api.JavaVersion

/**
 * @author Davide Giuseppe Farella.
 * An object containing params for the App.
 */
object Project {

    /** The [String] id of the App */
    val id = "studio.forface.viewstatestore"
    /** The Android API level as target of the App */
    val targetSdk = 28
    /** The Android API level required for run the App */
    val minSdk = 14
    /** The version of the JDK  */
    val jdkVersion = JavaVersion.VERSION_1_8

    private val major:      Int =       1
    private val minor:      Int =       0
    private val channel:    Channel =   Stable
    private val patch:      Int =       0
    private val build:      Int =       0


    // ====================   S T A T I C   C O D E   ==================== //

    /** @return the [Int] version code of the App, resolved from [major], [minor], [channel], [patch] and [build] */
    val versionCode: Int get() {
        // pattern:
        // major minor channel patch build
        // 00    00    0      00     00

        val build   = build         *            1
        val patch   = patch         *         1_00
        val channel = channel.value *      1_00_00
        val minor   = minor         *    1_0_00_00
        val major   = major         * 1_00_0_00_00

        return major + minor + channel + patch + build
    }

    /**
     * @return the [String] version name of the App, resolved from [major], [minor], [channel], [patch] and [build]
     * @see versionNameSuffix
     */
    val versionName get() = "$major.$minor$versionNameSuffix"

    /**
     * @return a [String] suffix for [versionName]. E.g. `-alpha-5`
     *
     * @throws IllegalArgumentException
     * @see preconditions
     */
    private val versionNameSuffix: String get() {
        preconditions()

        val number = if ( channel is Build ) buildNumber else build
        return "${channel.suffix}${channelNumberString( number )}"
    }

    /** @return the [Int] number for the build, needed only if [channel] is [Build] */
    private val buildNumber: Int get() {
        // pattern:
        // channel patch build
        // 0      00     00
        val build   = build         *            1
        val patch   = patch         *         1_00
        val channel = channel.value *      1_00_00

        return channel + patch + build
    }

    /** @return a [String] representing the number of the version */
    private fun channelNumberString( number: Int ) =
        if ( number > 0 ) "-$number" else ""

    /**
     * Check the version's numbers are valid.
     * @throws IllegalArgumentException
     */
    private fun preconditions() {
        if ( channel is Build && build < 1 )
            throw IllegalArgumentException( "'Build number' must be greater than 0 if 'channel' is 'Build'" )

        if ( channel is Stable && build > 0 )
            throw  IllegalArgumentException(
                "'Stable channel' can't have a `build number` greater than 0 increase the 'minor' for the next build"
            )
    }

    /** A sealed class for the Channel of the Version of the App */
    @Suppress("unused")
    sealed class Channel(val value: Int, val suffix: String ) {
        object Build :      Channel(0,"-build" )
        object Alpha :      Channel(1,"-alpha" )
        object Beta :       Channel(2,"-beta" )
        object RC :         Channel(3,"-rc" )
        object Stable :     Channel(4,"" )
    }
}
