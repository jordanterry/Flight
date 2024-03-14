plugins {
	alias(libs.plugins.kotlin.jvm)
}

kotlin {
	jvmToolchain(JavaLanguageVersion.of(17).asInt())
}

dependencies {
	api(libs.kotlinx.datetime)
	api("com.github.kittinunf.result:result-jvm:5.5.0")
}