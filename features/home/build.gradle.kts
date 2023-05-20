plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.features.home"
}

dependencies {
    api(projects.features.home.api)
    api(projects.features.home.di)
    api(projects.features.home.implementation)
}