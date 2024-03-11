plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.square.anvil)
}

anvil {
    generateDaggerFactories = true
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

dependencies {
    api(projects.foundation.di)
    api(platform(libs.square.okhttp.platform))
    api(libs.square.okhttp)
    api(libs.square.okhttp.logging.interceptor)
    api(libs.kotlinx.coroutines)
    api(libs.kotlinx.serialization.json)
    testImplementation(libs.square.okhttp.mockwebserver)
}