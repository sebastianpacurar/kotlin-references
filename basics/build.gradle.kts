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

    // used for reading from/ writing to Word, Excel, PowerPoint files
    implementation("org.apache.poi:poi-ooxml:5.2.5")

    // used for working with coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
}
