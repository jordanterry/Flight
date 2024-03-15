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

include(":app")
include(":foundation:circuit")
include(":foundation:compose")
include(":foundation:di")
include(":foundation:models")
include(":foundation:networking")
include(":foundation:o11y")
include(":features:search")
include(":services:flightaware")
include(":services:search")
include(":services:arrivals")
include(":services:operator")