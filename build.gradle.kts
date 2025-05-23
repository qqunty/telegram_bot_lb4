plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories { mavenCentral() }

configurations.all {
    exclude(group = "com.fasterxml.jackson.module", module = "jackson-module-jaxb-annotations")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.quartz-scheduler:quartz:2.3.2")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")

    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")

    runtimeOnly("com.h2database:h2:2.2.224")

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
    runtimeOnly("org.glassfish.jaxb:jaxb-runtime:4.0.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

springBoot {
    mainClass.set("com.qqunty.telegrambot.TelegramBotLb4ApplicationKt")
}
