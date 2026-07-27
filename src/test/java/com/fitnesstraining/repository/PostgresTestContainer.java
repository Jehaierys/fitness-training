package com.fitnesstraining.repository;


import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface PostgresTestContainer {

    // todo: credentials?
    @Container
    PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:16-bookworm")
            .withDatabaseName("fitness")
            .withUsername("postgres")
            .withPassword("1234");

//     todo: what's this?
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
//        registry.add("spring.datasource.username", POSTGRES::getUsername);
//        registry.add("spring.datasource.password", POSTGRES::getPassword);
//    }
}
