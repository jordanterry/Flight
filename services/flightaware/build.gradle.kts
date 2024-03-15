plugins {
	id("jt.flights.jvm")
	alias(libs.plugins.square.anvil)
	alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.gmazzo.buildconfig)
}

anvil {
	generateDaggerFactories = true
}

buildConfig {
	buildConfigField("FLIGHTAWARE_TOKEN", property("flight.flightaware.token").toString())
}

dependencies {
	api(projects.foundation.di)
	api(projects.foundation.networking)
	api(projects.foundation.models)
	api(libs.kotlinx.datetime)
}