plugins {
	id("jt.flights.android.library")
	alias(libs.plugins.square.anvil)
	alias(libs.plugins.kotlin.ksp)
}

android {
	namespace = "jt.flights.service.search"
}

anvil {
	generateDaggerFactories = true
}

dependencies {
	api(projects.foundation.models)
	api(projects.foundation.di)
	api(projects.services.flightaware)
	api(projects.services.operator)
	implementation(projects.foundation.networking)
	implementation(libs.kotlinx.coroutines)
	implementation(libs.android.agent)
	implementation(libs.androidx.room.runtime)
//	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)
}