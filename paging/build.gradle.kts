plugins {
    id( "com.android.library" )
    id( "kotlin-android" )
}

android { applyAndroidConfig() }

dependencies {
    implementation( project( ":viewstatestore" ) )
    applyAndroidTests()

    api( Libs.Android.paging )
    androidTestImplementation( Libs.Android.paging_testing )
}

publish( "viewstatestore-paging" )
applyDokka()