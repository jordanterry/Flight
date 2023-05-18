import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroid
import extensions.configureJvm
import extensions.configureKotlin
import extensions.configureSpotless
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val javaVersion = JavaVersion.VERSION_1_8
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            extensions.configure<ApplicationExtension> {
                configureAndroid(this, javaVersion)
            }
            configureJvm(javaVersion)
            configureKotlin(javaVersion)
            configureSpotless()
        }
    }
}