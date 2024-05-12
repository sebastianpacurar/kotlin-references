plugins {
    kotlin("jvm")
    alias(libs.plugins.serialization) apply false
}

group = "kotlin.references"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // okhttp for performing api calls
    implementation(libs.okhttp)

    // used for serialization/deserialization
    implementation(libs.kotlinx.serialization.json)

    // used for reading from/ writing to Word, Excel, PowerPoint files
    implementation(libs.apache.poi.ooxml)

    // used for working with coroutines
    implementation(libs.kotlinx.coroutines.core)

    // used for writing unit tests (this would normally go in the test module, by using testImplementation("package"))
    implementation(libs.junit)

    // used for logging in the console
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
}
