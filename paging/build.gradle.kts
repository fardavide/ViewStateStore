import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("com.jfrog.bintray" )
}

android { applyAndroidConfig() }

dependencies {
    implementation( project(":viewstatestore" ) )
    applyAndroidTests()

    api( Libs.Android.paging )
    androidTestImplementation( Libs.Android.paging_testing )
}

publish("viewstatestore-paging" ) {
    bintray {
        user = typedProperty("bintray.user" )
        key = typedProperty("bintray.apikey" )

        pkg( delegateClosureOf<BintrayExtension.PackageConfig> {
            repo = extra.typedGet( "bintrayRepo" )
            name = extra.typedGet( "bintrayName" )
            desc = extra.typedGet( "libraryDescription" )
            websiteUrl = extra.typedGet( "siteUrl" )
            vcsUrl = extra.typedGet( "gitUrl" )
            setLicenses( * extra.typedGet( "allLicenses" ) )
            dryRun = false
            publish = true
            override = true
            publicDownloadNumbers = true

            // version {
            //     desc = extra.typedGet( "libraryDescription")
            // }
        } )
    }
}