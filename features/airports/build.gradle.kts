plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.features.airports"
}

dependencies {
    api(projects.features.airports.api)
    api(projects.features.airports.di)
    api(projects.features.airports.internal)
}