plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "kotlin.references"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    // okhttp for performing api calls
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // used for serialization/deserialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}