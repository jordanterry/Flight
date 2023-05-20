plugins {
    id("jt.android.library")
    kotlin("plugin.parcelize")
}

android {
    namespace = "jt.flight.search.api"
}

dependencies {
    implementation(libs.slack.circuit)
}