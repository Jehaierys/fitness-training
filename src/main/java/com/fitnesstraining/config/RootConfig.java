package com.fitnesstraining.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@Profile({"prod", "dev"})
@ComponentScan("com.example")
@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application-dev.properties"
})
public class RootConfig {

}