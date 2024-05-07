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

    // used for writing unit tests (this would normally go in the test module, by using testImplementation("package"))
    implementation("junit:junit:4.13.1")

    // used for logging in the console
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")
}
