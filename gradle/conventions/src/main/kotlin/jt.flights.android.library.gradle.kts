import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

(extensions["android"] as LibraryExtension).apply {
	compileSdk = 34
	defaultConfig {
		minSdk = 28
	}
}

extensions.configure<KotlinAndroidProjectExtension> {
	jvmToolchain(17)
	explicitApi()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions {
		jvmTarget.set(JvmTarget.fromTarget("17"))
		allWarningsAsErrors.set(true)
	}
}
