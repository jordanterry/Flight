plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.flight.flightaware.impl"
}

dependencies {
    implementation(projects.services.flightaware.api)
}