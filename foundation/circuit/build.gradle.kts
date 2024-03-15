plugins {
	id("jt.flights.android.library")
	alias(libs.plugins.square.anvil)
}

android {
	namespace = "jt.flights.foundations.circuit"
}

anvil {
	generateDaggerFactories = true
}

dependencies {
	api(projects.foundation.di)
	api(projects.foundation.compose)
	api(libs.bundles.slack.circuit)
}