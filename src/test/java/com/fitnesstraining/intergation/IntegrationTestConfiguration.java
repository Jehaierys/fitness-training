package com.fitnesstraining.intergation;

import com.fitnesstraining.config.test.JacksonTestConfig;
import com.fitnesstraining.config.test.PostgresTestContainer;
import io.cucumber.java.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@Import({
        JacksonTestConfig.class
})
public class IntegrationTestConfiguration implements PostgresTestContainer {

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @AfterAll
    public static void stopContainers() {
        POSTGRES.stop();
    }
}