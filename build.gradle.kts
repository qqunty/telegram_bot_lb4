plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.+")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")
    runtimeOnly("com.h2database:h2:2.2.224")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

springBoot {
    mainClass.set("com.qqunty.telegrambot.TelegramBotLb4ApplicationKt")
}
