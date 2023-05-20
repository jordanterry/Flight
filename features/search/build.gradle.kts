plugins {
    id("jt.android.library")
}

android {
    namespace = "jt.features.search"
}

dependencies {
    api(projects.features.search.api)
    api(projects.features.search.di)
    api(projects.features.search.implementation)
}