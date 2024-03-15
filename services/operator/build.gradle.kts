plugins {
	id("jt.flights.jvm")
	alias(libs.plugins.square.anvil)
}

anvil {
	generateDaggerFactories = true
}

dependencies {
	api(projects.foundation.models)
	api(projects.foundation.di)
	api(projects.services.flightaware)
	implementation(projects.foundation.networking)
	implementation(libs.kotlinx.coroutines)
	implementation(libs.android.agent)
}
