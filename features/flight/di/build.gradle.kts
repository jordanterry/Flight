plugins {
    id("jt.android.library")
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "jt.flight.flight.di"
}

dependencies {
    api(projects.features.flight.api)
    implementation(projects.features.flight.internal)

    implementation(libs.slack.circuit)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
}