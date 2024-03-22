import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.squareup.sort-dependencies")
}

(extensions["android"] as ApplicationExtension).apply {
	compileSdk = 34
	defaultConfig {
		targetSdk = 34
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

