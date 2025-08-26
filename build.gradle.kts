plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.flywaydb.flyway") version "6.4.3"
}

group = "com.mailson.pereira.tech.assessment"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}
subprojects {
	allprojects {
		apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
		apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
		apply(plugin = "org.jetbrains.kotlin.jvm")
		apply(plugin = "kotlin-allopen")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.5")
		implementation("org.springframework.boot:spring-boot-starter-web:3.3.5")
		implementation("org.springframework.boot:spring-boot-starter-validation:3.3.5")

		implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.springfox:springfox-bean-validators:3.0.0")

		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

		implementation("org.flywaydb:flyway-core:11.8.2")

		implementation("ch.qos.logback:logback-core:1.5.18")
		implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
		implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
		implementation("ch.qos.logback:logback-classic:1.5.18")

		runtimeOnly("com.h2database:h2:2.3.232")

		testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.5")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
		testImplementation("org.mockito:mockito-core:4.8.0")
		testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
		testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	allOpen {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set("com.mailson.pereira.tech.assessment.MailsonPereiraTechAssessmentApplicationKt")
}
