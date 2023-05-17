import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroid
import extensions.configureJvm
import extensions.configureKotlin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            extensions.configure<ApplicationExtension> {
                configureAndroid(this)
            }
            configureJvm(JavaVersion.VERSION_11)
            configureKotlin(JavaVersion.VERSION_11)
        }
    }
}