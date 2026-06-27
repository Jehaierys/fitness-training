package com.fitnesstraining.config;


import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.Session;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class StorageConfig {

    @Bean
    public Map<Long, User> userStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Long, Coach> mentorStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Long, Trainee> traineeStorage() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Long, Session> sessionStorage() {
        return new ConcurrentHashMap<>();
    }
}