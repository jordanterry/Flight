import com.android.build.api.dsl.LibraryExtension
import extensions.configureAndroid
import extensions.configureJvm
import extensions.configureKotlin
import extensions.configureSpotless
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val javaVersion = JavaVersion.VERSION_1_8
        with (target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            extensions.configure<LibraryExtension> {
                configureAndroid(this, javaVersion)
            }
            configureJvm(javaVersion)
            configureKotlin(javaVersion)
            configureSpotless()
        }
    }
}