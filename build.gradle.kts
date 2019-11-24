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
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
