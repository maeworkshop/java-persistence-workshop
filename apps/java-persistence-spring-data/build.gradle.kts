plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("me.champeau.jmh") version "0.6.8"
}

group = "com.maemresen"
version = "0.0.1-SNAPSHOT"

val testContainers = "1.19.8"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.projectlombok:lombok")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-testcontainers")
    implementation(platform("org.testcontainers:testcontainers-bom:$testContainers"))
    implementation("org.testcontainers:junit-jupiter")
    implementation("org.testcontainers:postgresql")

    jmh(project(":libs:jmh-utils"))

    compileOnly("org.flywaydb:flyway-database-postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
