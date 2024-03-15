plugins {
	id("jt.flights.android.library")
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.square.anvil)
	alias(libs.plugins.kotlin.ksp)
}

android {
	namespace = "jt.flights.search"

	buildFeatures {
		buildConfig = true
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
	}
}

anvil {
	generateDaggerFactories = true
}

dependencies {
	api(projects.foundation.di)
	api(projects.services.arrivals)
	api(projects.services.search)

	implementation(projects.foundation.circuit)

	implementation(libs.android.agent)

	ksp(libs.slack.circuit.codegen)

	debugImplementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.ui.tooling)
}