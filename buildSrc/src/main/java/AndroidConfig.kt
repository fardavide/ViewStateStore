import com.android.build.gradle.TestedExtension
import studio.forface.easygradle.dsl.android.version

@Suppress("unused")
fun TestedExtension.applyAndroidConfig() {
    compileSdkVersion(Project.targetSdk)
    defaultConfig {
        minSdkVersion(Project.minSdk)
        targetSdkVersion(Project.targetSdk)
        version = Project.version
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {}
    }
    compileOptions {
        sourceCompatibility = Project.jdkVersion
        targetCompatibility = Project.jdkVersion
    }
    testOptions.unitTests.isIncludeAndroidResources = true
}
