import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.maemresen"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val testcontainersVersion = "1.20.1"
val lombokVersion = "1.18.34"
val queryDslVersion = "5.1.0"
val jakartaPersistenceApiVersion = "3.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("com.querydsl:querydsl-core:${queryDslVersion}")
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    implementation("com.querydsl:querydsl-sql:${queryDslVersion}")
    implementation("com.querydsl:querydsl-sql-spring:${queryDslVersion}")

    compileOnly("org.projectlombok:lombok")

    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")

    testCompileOnly("org.projectlombok:lombok")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api") // Use the latest version available

    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
