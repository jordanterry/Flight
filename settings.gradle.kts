@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }

    }
    versionCatalogs {
        create("test") {
            from(files("gradle/test.versions.toml"))
        }
    }
}
rootProject.name = "Flights"

include(":app")

include(":features:home")
include(":features:home:api")
include(":features:home:implementation")
include(":features:home:di")
include(":features:search")
include(":features:search:api")
include(":features:search:implementation")
include(":features:search:di")

include(":services:flightaware:api")
include(":services:flightaware:impl")
include(":services:flightaware:di")