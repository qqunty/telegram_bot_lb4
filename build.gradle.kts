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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2:2.2.224")
    runtimeOnly("javax.xml.bind:jaxb-api:2.3.1")
    runtimeOnly("org.glassfish.jaxb:jaxb-runtime:2.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

springBoot {
    mainClass.set("com.qqunty.telegrambot.TelegramBotLb4ApplicationKt")
}
