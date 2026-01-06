plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    kotlin("plugin.jpa") version "2.2.20"
}

group = "pl.prompthub"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    /* ================= SPRING CORE ================= */
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    /* ================= KOTLIN ================= */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    /* ================= JWT ================= */
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    /* ================= DATABASE ================= */
    runtimeOnly("org.postgresql:postgresql")

    /* ================= SECURITY ================= */
    implementation("org.springframework.security:spring-security-crypto")

    /* ================= FIREBASE ================= */
    implementation("com.google.firebase:firebase-admin:9.2.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}