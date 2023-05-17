import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroid
import extensions.configureJvm
import extensions.configureKotlin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val javaVersion = JavaVersion.VERSION_1_8
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureJvm(javaVersion)
            configureKotlin(javaVersion)
        }
    }
}