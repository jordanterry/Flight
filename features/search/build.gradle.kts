plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.square.anvil)
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
}