
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(libs.agent.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.square.anvil) apply false
}