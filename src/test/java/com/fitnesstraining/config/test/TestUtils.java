package com.fitnesstraining.config.test;

import com.fitnesstraining.utils.SessionTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@Slf4j
@TestConfiguration
@Import(SessionTypes.class)
public class TestUtils {

    @Bean
    public SessionTypes sessionTypes() {
        return new SessionTypes();
    }
}
