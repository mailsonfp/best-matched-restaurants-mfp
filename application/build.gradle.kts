plugins {
    kotlin("jvm")
    id("org.springframework.boot") version Versions.springBoot
}

group = "com.mailson.pereira.tech.assessment"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":input-boundary"))
    implementation(project(":output-boundary"))
    implementation(project(":repository"))
    implementation(project(":service"))
    implementation(project(":web"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(Versions.javaVersion)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    archiveFileName.set("best-matched-restaurants-mfp-0.0.1-SNAPSHOT.jar")
    mainClass.set(Versions.projectMainClassName)
}