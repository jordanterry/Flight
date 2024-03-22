import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	id("org.jetbrains.kotlin.jvm")
	id("com.android.lint")
	id("com.squareup.sort-dependencies")
}

extensions.configure<KotlinJvmProjectExtension> {
	jvmToolchain(17)
	explicitApi()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions {
		jvmTarget.set(JvmTarget.fromTarget("17"))
		allWarningsAsErrors.set(true)
	}
}
