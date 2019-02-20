plugins {
    id("com.android.library" )
    id("kotlin-android" )
}

android { applyAndroidConfig() }

dependencies {
    applyAndroidTests()

    api( Libs.kotlin )
    api( Libs.Android.livedata )
}
