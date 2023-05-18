package extensions

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

internal fun Project.configureJvm(javaVersion: JavaVersion) {
    extensions.configure(JavaPluginExtension::class.java) {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
