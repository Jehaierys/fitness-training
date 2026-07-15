
plugins {
    java
}

group = "com.example.fitness-training"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Framework
    implementation("org.springframework:spring-context:7.0.0")
    implementation("org.springframework:spring-webmvc:7.0.0")
    implementation("org.springframework:spring-jdbc:7.0.0")
    implementation("org.springframework:spring-tx:7.0.0")
    implementation("org.springframework:spring-orm:7.0.0")
    implementation("org.springframework:spring-aop:7.0.0")

    // Tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.13")
    implementation("org.apache.tomcat.embed:tomcat-embed-el:11.0.13")
    implementation("org.apache.tomcat.embed:tomcat-embed-websocket:11.0.13")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // Spring Security
    implementation("org.springframework.security:spring-security-config:7.0.0")
    implementation("org.springframework.security:spring-security-web:7.0.0")

    // Validation
    implementation("org.hibernate.validator:hibernate-validator:9.0.1.Final")

    // JPA
    implementation("org.hibernate.orm:hibernate-core:7.1.0.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("com.zaxxer:HikariCP:6.3.1")

    // PostgreSQL
    runtimeOnly("org.postgresql:postgresql:42.7.7")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testImplementation("org.springframework:spring-test:7.0.0")
    testImplementation("org.springframework.security:spring-security-test:7.0.0")

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}