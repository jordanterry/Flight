plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.kotlin.parcelize) apply false
	alias(libs.plugins.square.sort.dependencies) apply false
	alias(libs.plugins.kotlin.multiplatform) apply false
	alias(libs.plugins.jetbrains.compose) apply false
	alias(libs.plugins.dependency.analysis)
}

dependencyAnalysis {
	issues {
		all {
			onAny {
				exclude("com.squareup.anvil:annotations")
			}
		}
	}
}