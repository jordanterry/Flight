plugins {
	alias(libs.plugins.kotlin.multiplatform)
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
}

kotlin {
	jvm()
    jvmToolchain(17)
    explicitApi()
	sourceSets {
		commonMain {
			dependencies {
				api(libs.kotlinx.datetime)
			}
		}
	}
}
