import extensions.configureJvm
import extensions.configureKotlin
import extensions.configureSpotless
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val javaVersion = JavaVersion.VERSION_1_8
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureJvm(javaVersion)
            configureKotlin(javaVersion)
            configureSpotless()
        }
    }
}