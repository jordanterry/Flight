plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.features.flight"
}

dependencies {
    api(projects.features.flight.api)
    api(projects.features.flight.di)
    api(projects.features.flight.internal)
}