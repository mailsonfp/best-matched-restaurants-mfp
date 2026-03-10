plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version Versions.kotlin
}

group = "com.mailson.pereira.tech.assessment"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":commons"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    apply(plugin = "kotlin-allopen")
    jvmToolchain(Versions.javaVersion)
}