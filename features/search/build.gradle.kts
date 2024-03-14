plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.square.anvil)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "jt.flights.search"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    defaultConfig {
        minSdk = 28
        buildConfigField("String", "FLIGHTAWARE_TOKEN", "\"" + property("flight.flightaware.token") + "\"")
    }
}

anvil {
    generateDaggerFactories = true
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

dependencies {
    api(projects.foundation.di)
    implementation(projects.foundation.circuit)
    implementation(projects.foundation.models)
    implementation(projects.foundation.networking)
    implementation(libs.android.agent)
    ksp(libs.slack.circuit.codegen)
    api(libs.kotlinx.datetime)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}