plugins {
	id("jt.flights.android.library")
}

android {
	namespace = "jt.flights.foundation.compose"
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
	}
}

dependencies {
	api(platform(libs.androidx.compose.bom))
	api(libs.androidx.compose.activity)
	api(libs.androidx.compose.foundation)
	api(libs.androidx.compose.ui)
	api(libs.androidx.compose.material)
}