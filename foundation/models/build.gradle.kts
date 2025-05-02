plugins {
	alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
	jvm()
    jvmToolchain(21)
    explicitApi()
	sourceSets {
		commonMain {
			dependencies {
				api(libs.kotlinx.datetime)
			}
		}
	}
}
