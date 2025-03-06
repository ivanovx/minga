plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.1"
}

group = "pro.ivanov"
version = "0.0.1"

application {
    mainClass = "pro.ivanov.ApplicationKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-cio")
    implementation("ch.qos.logback:logback-classic")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("com.dropbox.core:dropbox-core-sdk")
    implementation("org.commonmark:commonmark")
    implementation("org.commonmark:commonmark-ext-gfm-tables")
    implementation("org.commonmark:commonmark-ext-yaml-front-matter")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
