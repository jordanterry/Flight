package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroid(
    extension: CommonExtension<*, *, *, *>,
    javaVersion: JavaVersion
) {
    with (extension) {
        compileSdk = 33
        defaultConfig {
            minSdk = 21
        }
        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
            isCoreLibraryDesugaringEnabled = true
        }
    }
    dependencies {
        add("coreLibraryDesugaring", "com.android.tools:desugar_jdk_libs:1.2.2")
    }
}
