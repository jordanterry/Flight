package extensions

import org.gradle.api.Project

internal fun Project.configureLint() {
    pluginManager.apply("com.android.lint")
}