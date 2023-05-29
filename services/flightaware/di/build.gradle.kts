plugins {
    id("jt.android.library")
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "jt.flight.flightaware.di"
}

dependencies {
    api(projects.services.flightaware.api)
    implementation(projects.services.flightaware.impl)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
}