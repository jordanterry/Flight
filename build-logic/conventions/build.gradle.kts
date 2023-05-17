plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
}

version = "0.1"

gradlePlugin {
    plugins {
        register("androidApplicationPlugin") {
            id = "jt.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidLibraryPlugin") {
            id = "jt.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("kotlinLibraryPlugin") {
            id = "jt.kotlin.library"
            implementationClass = "KotlinLibraryPlugin"
        }
    }
}