plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.square.anvil)
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
    api(projects.services.arrivals)
    api(projects.services.search)

    implementation(projects.foundation.circuit)

    implementation(libs.android.agent)

    ksp(libs.slack.circuit.codegen)

    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}