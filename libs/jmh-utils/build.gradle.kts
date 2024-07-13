plugins {
    `java-library`
    id("org.springframework.boot") version "3.3.1" apply false
    id("io.spring.dependency-management") version "1.1.5"
    id("me.champeau.jmh") version "0.6.8"
}

val testContainers = "1.19.8"
val jmhVersion = "1.37"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql")
    implementation(platform("org.testcontainers:testcontainers-bom:$testContainers"))
    implementation("org.testcontainers:junit-jupiter")
    implementation("org.testcontainers:postgresql")
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework:spring-context")
    implementation("org.projectlombok:lombok")

    api("org.openjdk.jmh:jmh-core:$jmhVersion")
    api("org.openjdk.jmh:jmh-generator-annprocess:$jmhVersion")

    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
