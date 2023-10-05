plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.square.anvil)
}

android {
    namespace = "jt.flights"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    kotlin {
        jvmToolchain(JavaLanguageVersion.of(11).asInt())
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    defaultConfig {
        applicationId = "jt.flights"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.bundles.slack.circuit)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.dagger)
    implementation(libs.square.anvil.annotations)
    kapt(libs.dagger.compiler)
}
