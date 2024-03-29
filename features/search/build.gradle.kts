plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.kotlinx.serialization)
}

kotlin {

	jvm {
		compilations.all {
			kotlinOptions {
				jvmTarget = "11"
			}
		}
	}

	explicitApi()

	sourceSets {
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
			implementation("io.kotest:kotest-assertions-core:5.8.1")
			implementation(libs.kotlinx.coroutines.test)
			implementation(libs.square.okhttp.mockwebserver)
		}
	}
}

// Required for Paparazzi
apply(from = "guava-fix.gradle")
