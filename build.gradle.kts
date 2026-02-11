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
    // Source: https://mvnrepository.com/artifact/org.springframework/spring-core
    implementation("org.springframework:spring-core:6.1.13")
    // Source: https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation("org.springframework:spring-context:6.1.13")
    //implementation("org.springframework.boot:spring-boot-starter")
    //annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // Source: https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("org.hibernate.orm:hibernate-core:7.2.3.Final")
    // Source: https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("org.projectlombok:lombok:1.18.38")

}

tasks.test {
    useJUnitPlatform()
}