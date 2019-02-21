@file:Suppress("unused")

import org.gradle.api.plugins.MavenRepositoryHandlerConvention
import org.gradle.api.tasks.Upload
import org.gradle.kotlin.dsl.*

/**
 * @author Davide Giuseppe Farella.
 * A file containing params for publishing.
 */

fun KotlinBuildScript.publish(
    artifact: String,
    bintrayConfig: () -> Unit
) = Project.let { p ->
    apply( plugin = "com.github.dcendents.android-maven" )
    apply( plugin = "com.jfrog.bintray" )

    val bintrayName = "${p.bintrayGroup}.$artifact"

    group = Project.groupId

    extra["bintrayRepo"] = p.name
    extra["bintrayName"] = bintrayName

    extra["libraryName"] = artifact

    extra["publishedGroupId"] = group
    extra["artifact"] = artifact
    extra["libraryVersion"] = p.versionName

    extra["libraryDescription"] = p.description
    extra["siteUrl"] = p.siteUrl
    extra["gitUrl"] = p.gitUrl
    extra["developerId"] = p.developerId
    extra["developerName"] = p.developerName
    extra["developerEmail"] = p.developerEmail
    extra["licenseName"] = p.licenseName
    extra["licenseUrl"] = p.licenseUrl
    extra["allLicenses"] = p.allLicenses

    tasks.named<Upload>("install" ) {
        repositories {
            withConvention( MavenRepositoryHandlerConvention::class ) {
                mavenInstaller {
                    pom.project {
                        withGroovyBuilder {
                            "packaging"(     "aar" )
                            "groupId"(       p.groupId )
                            "artifactId"(    artifact )
                            "name"(          artifact )
                            "description"(   p.description )
                            "url"(           p.siteUrl )
                            "licenses" {
                                "license" {
                                    "name"( p.licenseName )
                                    "url"( p.licenseUrl )
                                }
                            }
                            "developers" {
                                "developer" {
                                    "id"( p.developerId )
                                    "name"( p.developerName )
                                    "email"( p.developerEmail )
                                }
                            }
                            "scm" {
                                "connection"( p.gitUrl )
                                "developerConnection"( p.gitUrl )
                                "url"( p.siteUrl )
                            }
                        }
                    }
                }
            }
        }
    }

    bintrayConfig()

    apply( from = "https://raw.githubusercontent.com/nuuneoi/JCenter/master/installv1.gradle" )
    apply( from = "https://raw.githubusercontent.com/nuuneoi/JCenter/master/bintrayv1.gradle" )
}