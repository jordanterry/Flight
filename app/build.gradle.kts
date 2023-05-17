plugins {
    id("jt.android.application")
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "uk.co.jordanterry.flights"

    defaultConfig {
        applicationId = "uk.co.jordanterry.flights"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled =  true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(projects.features.flight)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.slack.circuit)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
}