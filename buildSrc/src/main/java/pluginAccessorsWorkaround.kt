@file:Suppress("RemoveRedundantBackticks", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import studio.forface.easygradle.dsl.android.`kotlin-android-extensions`
import studio.forface.easygradle.dsl.android.`kotlin-android`
import studio.forface.easygradle.dsl.detekt

/*
 * A set of references to `PluginDependenciesSpec` from EasyGradle that are not being resolved in the
 * project
 */

val PluginDependenciesSpec.`detekt` get() = `detekt`
val PluginDependenciesSpec.`kotlin-android` get() = `kotlin-android`
val PluginDependenciesSpec.`kotlin-android-extensions` get() = `kotlin-android-extensions`
