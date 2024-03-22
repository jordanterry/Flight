@file:Suppress("UnstableApiUsage")

pluginManagement {
	includeBuild("gradle/conventions")
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}
rootProject.name = "Flights"
plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

include(":app")
include(":foundation:models")
include(":foundation:networking")
include(":features:search")
include(":services:flightaware")
include(":services:search")
include(":services:operator")