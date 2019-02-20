@file:Suppress("PropertyName")

buildscript {
    repositories( repos )
    dependencies( classpathDependencies )
}

allprojects {
    repositories( repos )
}

tasks.register("clean", Delete::class.java ) {
    delete( rootProject.buildDir )
}