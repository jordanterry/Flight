plugins {
	id("jt.flights.android.library")
	alias(libs.plugins.gmazzo.buildconfig)
}

android {
	namespace = "jt.flights.service.search"
}

buildConfig {
	buildConfigField( "NEWRELIC_TOKEN", property("flight.newrelic.token").toString())
}

dependencies {
	implementation(libs.android.agent)
	implementation(libs.androidx.startup)
}