package extensions

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
    pluginManager.apply(SpotlessPlugin::class)

    extensions.configure<SpotlessExtension> {
        ratchetFrom("origin/main")
        kotlin {
            target("**/*.kt")
            target("**/build/**/*.kt")
            ktlint()
                .userData(mapOf("android" to "true"))
                .setEditorConfigPath("$rootDir/.editorconfig")

        }
        kotlinGradle {
            target("**/*.kts")
            target("**/build/**/*.kts")
            ktlint()
                .userData(mapOf("android" to "true"))
                .setEditorConfigPath("$rootDir/.editorconfig")
        }
    }
}