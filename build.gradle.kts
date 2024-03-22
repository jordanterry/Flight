plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.android.library) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.kotlin.jvm) apply false
	alias(libs.plugins.kotlin.parcelize) apply false
	alias(libs.plugins.gmazzo.buildconfig) apply false
	alias(libs.plugins.square.sort.dependencies) apply false
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