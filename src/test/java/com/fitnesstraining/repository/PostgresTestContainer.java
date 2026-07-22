package com.fitnesstraining.repository;


import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.PostgreSQLR2DBCDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;

public interface PostgresTestContainer {

    @Container
    PostgreSQLR2DBCDatabaseContainer container
            = new PostgreSQLR2DBCDatabaseContainer(
                    new PostgreSQLContainer<>("postgres:15.3")
    );
}
