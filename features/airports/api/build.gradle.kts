plugins {
    id("jt.android.library")
    kotlin("plugin.parcelize")
}

android {
    namespace = "jt.flight.airports.api"
}

dependencies {
    implementation(libs.slack.circuit)
}