@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
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
include(":features:search")
include(":services:flightaware")
include(":services:search")
include(":services:arrivals")