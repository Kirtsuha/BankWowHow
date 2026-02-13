plugins {
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context")

    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.projectlombok:lombok")

    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}