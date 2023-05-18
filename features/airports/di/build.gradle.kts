plugins {
    id("jt.android.library")
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "jt.flight.airports.di"
}

dependencies {
    api(projects.features.airports.api)
    implementation(projects.features.airports.internal)

    implementation(libs.slack.circuit)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
}