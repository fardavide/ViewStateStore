import com.android.build.gradle.TestedExtension

@Suppress("unused")
fun TestedExtension.applyAndroidConfig() {
    compileSdkVersion( Project.targetSdk )
    defaultConfig {
        minSdkVersion( Project.minSdk )
        targetSdkVersion( Project.targetSdk )
        versionCode = Project.versionCode
        versionName = Project.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName( "release" ) {
            isMinifyEnabled = false
            proguardFiles( getDefaultProguardFile("proguard-android.txt" ), "proguard-rules.pro" )
        }
        getByName("debug" ) {}
    }
    compileOptions {
        sourceCompatibility = Project.jdkVersion
        targetCompatibility = Project.jdkVersion
    }
    testOptions.unitTests.isIncludeAndroidResources = true
}