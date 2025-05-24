// build.gradle.kts
plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.qqunty"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {

    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz") // Quartz
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    val tgVersion = "6.9.7.1"

    implementation("org.telegram:telegrambots:$tgVersion")
    implementation("org.telegram:telegrambots-spring-boot-starter:$tgVersion")
    implementation("org.telegram:telegrambotsextensions:$tgVersion")

    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
