plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.services.flightaware"
}

dependencies {
    api(projects.services.flightaware.api)
    api(projects.services.flightaware.di)
    api(projects.services.flightaware.impl)
}