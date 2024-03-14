plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
}

android {
	compileSdk = 34
	namespace = "jt.flights.foundation.compose"
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
	}
}

kotlin {
	jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

dependencies {
	api(platform(libs.androidx.compose.bom))
	api(libs.androidx.compose.activity)
	api(libs.androidx.compose.foundation)
	api(libs.androidx.compose.ui)
	api(libs.androidx.compose.material)
}