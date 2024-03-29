plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)

    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
	androidTarget()
	@Suppress("OPT_IN_USAGE")
	compilerOptions {
		jvm {
			jvmToolchain(11)
			explicitApi()
		}
	}

	sourceSets {

        androidMain.dependencies {
            implementation(libs.androidx.compose.activity)
			implementation(libs.android.driver)
			implementation(projects.features.search)
        }
        commonMain.dependencies {
			implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.circuit.foundation)

			implementation(libs.square.okhttp)
			implementation(projects.foundation.networking)
			implementation(projects.services.flightaware)
			implementation(projects.services.search)
        }
    }
}


android {

	sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
	sourceSets["main"].res.srcDirs("src/androidMain/res")
	sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        buildConfig = true
    }

    namespace = "jt.flights"
	compileSdk = 34
    defaultConfig {
		targetSdk = 34
		minSdk = 28
        applicationId = "jt.flights"
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		buildConfigField("String", "FLIGHT_AWARE_TOKEN", "\"" + System.getenv("FLIGHT_AWARE_TOKEN").toString() + "\"")
    }

    lint {
        checkDependencies = true
        ignoreTestSources = true
    }


	dependencies {
		implementation(libs.compose.ui.tooling)
	}
}
