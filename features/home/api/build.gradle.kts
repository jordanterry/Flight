plugins {
    id("jt.android.library")
    kotlin("plugin.parcelize")
}

android {
    namespace = "jt.flight.home.api"
}

dependencies {
    implementation(libs.slack.circuit)
}