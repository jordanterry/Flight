plugins {
    id("jt.android.library")
    kotlin("plugin.parcelize")
}

android {
    namespace = "jt.flight.flight.api"
}

dependencies {
    implementation(libs.slack.circuit)
}