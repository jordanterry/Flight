plugins {
	`kotlin-dsl`
	id("java-gradle-plugin")
}

dependencies {
	implementation(libs.plugins.android.application.asDependency())
	implementation(libs.plugins.android.library.asDependency())
	implementation(libs.plugins.kotlin.android.asDependency())
	implementation(libs.plugins.kotlin.jvm.asDependency())
	implementation(libs.plugins.square.sort.dependencies.asDependency())
}

kotlin {
	jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions {
		allWarningsAsErrors.set(true)
	}
}

fun Provider<PluginDependency>.asDependency(): Provider<String> =
	this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
