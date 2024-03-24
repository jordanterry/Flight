plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.kotlinx.serialization)
	id("app.cash.paparazzi") version("1.3.3")
}

kotlin {
	androidTarget {
		compilations.all {

			compileJavaTaskProvider.configure {
				targetCompatibility = "11"
				sourceCompatibility = "11"
			}
			kotlinOptions {
				jvmTarget = "11"
			}
		}
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
			implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
		}

		commonTest.dependencies {
			implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
			implementation(libs.circuit.test)
			implementation(libs.kotlin.test)
			implementation(libs.kotlinx.coroutines.test)
			implementation(libs.square.okhttp.mockwebserver)
		}
	}
}

android {
	namespace = "jt.flights.features.search"
	compileSdk = 34
	testOptions.unitTests.isReturnDefaultValues = true

	dependencies {
		implementation(libs.compose.ui.tooling)
	}
}

apply(from = "guava-fix.gradle")
