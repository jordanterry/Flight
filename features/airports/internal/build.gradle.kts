plugins {
    id("jt.android.library")
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "jt.flight.airports.internal"
}

dependencies {
    api(projects.features.airports.api)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.slack.circuit)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
}