plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(JavaLanguageVersion.of(17).asInt())
}