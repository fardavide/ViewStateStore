@file:Suppress("MayBeConstant", "RemoveRedundantBackticks")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.maven
import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.`android-arch-testing`
import studio.forface.easygradle.dsl.android.`android-gradle-plugin`
import studio.forface.easygradle.dsl.android.`lifecycle-runtime`

val repos: RepositoryHandler.() -> Unit
    get() = {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
    }

val ScriptHandlerScope.classpathDependencies: DependencyHandlerScope.() -> Unit
    get() = {
        classpath(`kotlin-gradle-plugin`)
        classpath(`android-gradle-plugin`)
    }

@Suppress("unused")
fun DependencyHandler.applyAndroidTests() {
    testImplementation(
        `kotlin-test`,
        `kotlin-test-junit`,
        `coroutines-test`,
        `android-arch-testing`,
        `lifecycle-runtime`,
        `mockk`
    )
}
