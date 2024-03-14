plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.square.anvil)
}

anvil {
    generateDaggerFactories = true
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

dependencies {
    api(projects.foundation.models)
    api(projects.foundation.di)
    api(projects.services.flightaware)
    implementation(projects.foundation.networking)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.android.agent)
}
