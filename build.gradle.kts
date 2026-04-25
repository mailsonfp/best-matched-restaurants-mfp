plugins {
	kotlin("jvm") version Versions.kotlin
	kotlin("plugin.spring") version Versions.kotlin
	kotlin("kapt") version Versions.kotlin
	id("org.springframework.boot") version Versions.springBoot
	id("io.spring.dependency-management") version Versions.springDependencyManagement
	kotlin("plugin.jpa") version Versions.kotlin
	id("org.flywaydb.flyway") version Versions.flywayPlugin
}

group = Versions.projectGroup
version = Versions.projectVersion

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(Versions.javaVersion)
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
		apply(plugin = "org.jetbrains.kotlin.kapt")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-web:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-actuator:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-validation:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-amqp:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf:${Versions.springBoot}")

		implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")
		implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
		implementation("io.springfox:springfox-bean-validators:${Versions.springFox}")

		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.openApi}")

		implementation("org.flywaydb:flyway-core:${Versions.flywayCore}")
		implementation("org.flywaydb:flyway-database-postgresql:${Versions.flywayCore}")

		implementation("ch.qos.logback:logback-core:${Versions.logback}")
		implementation("ch.qos.logback:logback-classic:${Versions.logback}")
		implementation("ch.qos.logback.contrib:logback-json-classic:${Versions.logbackContrib}")
		implementation("ch.qos.logback.contrib:logback-jackson:${Versions.logbackContrib}")

		implementation("com.vladmihalcea:hibernate-types-60:${Versions.hibernateTypes}")
		implementation("com.google.code.gson:gson:${Versions.gson}")

		implementation("org.mapstruct:mapstruct:${Versions.mapStruct}")
		kapt("org.mapstruct:mapstruct-processor:${Versions.mapStruct}")

		implementation("org.postgresql:postgresql:${Versions.postgres}")

		implementation("org.springframework.boot:spring-boot-starter-security:${Versions.springBoot}")
		implementation("org.springframework.boot:spring-boot-starter-data-redis:${Versions.springBoot}")
		implementation("io.jsonwebtoken:jjwt-api:${Versions.jsonWebToken}")

		runtimeOnly("io.jsonwebtoken:jjwt-impl:${Versions.jsonWebToken}")
		runtimeOnly("io.jsonwebtoken:jjwt-jackson:${Versions.jsonWebToken}")

		testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${Versions.kotlin}")
		testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
		testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
		testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
		testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}")
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

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			jvmTarget = Versions.javaVersion.toString()
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}

}

