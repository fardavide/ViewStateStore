@file:Suppress("unused")

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension

inline fun <reified T> Project.typedProperty( s: String ) = project.findProperty( s ) as? T

inline fun <reified T> ExtraPropertiesExtension.typedGet( name: String ) = get( name ) as? T