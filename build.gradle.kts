@file:Suppress("RemoveRedundantBackticks")

import io.gitlab.arturbosch.detekt.DetektPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    // Initialize versions of libraries
    initVersions()

    repositories(repos)
    dependencies(classpathDependencies)
}

allprojects {
    repositories(repos)
}

plugins {
    `detekt`
}

subprojects {
    // Options for Kotlin
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs +
                    "-XXLanguage:+NewInference" +
                    "-Xuse-experimental=kotlin.Experimental"
        }
    }

    // Disable JavaDoc
    tasks.withType<Javadoc> { enabled = false }

    // Configure Detekt
    apply<DetektPlugin>()

    detekt {
        failFast = false // fail build on any finding
        buildUponDefaultConfig = true // preconfigure defaults
        config = files("$rootDir/config/detekt.yml") // point to your custom config defining rules to run
//        baseline = file("$rootDir/config/baseline.xml") // a way of suppressing issues before introducing detekt

        reports {
            html.enabled = true // observe findings in your browser with structure and code snippets
            xml.enabled = false // checkstyle like format mainly for integrations like Jenkins
            txt.enabled = false // similar to the console output, contains issue signature to edit baseline files
        }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
