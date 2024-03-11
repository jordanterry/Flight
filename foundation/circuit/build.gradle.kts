plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.square.anvil)
}

android {
    compileSdk = 34
    namespace = "jt.flights.foundations.circuit"
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

anvil {
    generateDaggerFactories = true
}

dependencies {
    api(projects.foundation.di)
    api(projects.foundation.compose)
    api(libs.bundles.slack.circuit)
}