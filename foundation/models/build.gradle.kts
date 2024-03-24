plugins {
	alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
	jvm()
	@Suppress("OPT_IN_USAGE")
	compilerOptions {
		jvm {
			jvmToolchain(11)
			explicitApi()
		}
	}
	sourceSets {
		commonMain {
			dependencies {
				api(libs.kotlinx.datetime)
			}
		}
	}
}
