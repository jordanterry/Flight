plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.cashapp.sqldelight)
}

kotlin {
    androidTarget()
    compilerOptions {
        jvmToolchain(21)
    }

	sourceSets {
		androidMain.dependencies {
			implementation(libs.compose.ui.tooling.preview)
		}
		commonMain.dependencies {
			api(libs.circuit.foundation)
			api(projects.foundation.models)
			api(projects.services.search)

			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material3)

			implementation(compose.ui)
			implementation(compose.components.resources)
			implementation(compose.components.uiToolingPreview)
			implementation(libs.kotlinx.serialization.json)
			implementation(libs.kotlinx.coroutines)
			implementation(libs.kotlinx.datetime)
			implementation(libs.cashapp.sqldelight.coroutinesExtensions)
		}

		commonTest.dependencies {
			implementation(libs.cashapp.sqldelight.driver)
			implementation(libs.circuit.test)
			implementation(libs.kotlin.test)
            implementation(libs.turbine)
			implementation(libs.kotlinx.coroutines.test)
			implementation(libs.square.okhttp.mockwebserver)
		}
	}
}

android {
	namespace = "jt.flights.features.search"
	compileSdk = 35
	testOptions.unitTests.isReturnDefaultValues = true

	dependencies {
		implementation(libs.compose.ui.tooling)
	}
}

apply(from = "guava-fix.gradle")
