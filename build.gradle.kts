import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.rmolcr"
version = "0.1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

plugins {
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.1.0"
	id("org.openapi.generator") version "7.2.0"
	id("org.springframework.boot") version "3.2.2"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("plugin.lombok") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.google.code.gson:gson")
	implementation("me.paulschwarz:spring-dotenv:4.0.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
	implementation("org.postgresql:postgresql:42.3.8")
	implementation("org.slf4j:slf4j-api")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.security:spring-security-web")

	//Dependencies for OpenAPI generated code for Twitter
	implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
	implementation("com.squareup.moshi:moshi-adapters:1.14.0")
	implementation("com.squareup.okhttp3:okhttp:4.11.0")

	runtimeOnly("com.h2database:h2:2.2.224")

	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "17"
	}
	dependsOn.add("openApiGenerateAll")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
	// Disable *-plain.jar generation
	enabled = false
}

val openApiConfig = mapOf(
		"snykVersion" to "2024-01-23"
)

val openapiSpecs = mapOf(
		"io.snyk" to "src/main/resources/openapi/snyk/${openApiConfig["snykVersion"]}.json"
)
openapiSpecs.forEach {
	tasks.create("openApiGenerate-${it.key}", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
		generatorName.set("kotlin")
		inputSpec.set("$rootDir/${it.value}")
		outputDir.set("$buildDir/generated/${it.key}")
		apiPackage.set("${it.key}.api")
		modelPackage.set("${it.key}.model")
		packageName.set("${it.key}")
		invokerPackage.set("${it.key}.invoker")

		configOptions.set(mapOf(
				"dateLibrary" to "java8",
				"omitGradleWrapper" to "true",
		))
	}
}
tasks.register("openApiGenerateAll") { dependsOn(openapiSpecs.keys.map { "openApiGenerate-$it" }) }

kotlin.sourceSets {
	main {
		kotlin.srcDir("build/generated/io.snyk")
	}
}