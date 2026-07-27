package com.fitnesstraining.config.security;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
//@RequiredArgsConstructor
public class SecurityConfiguration {

    private final Configurers configurers;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(Configurers configurers, @Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.configurers = configurers;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(configurers::cors)

                .authorizeHttpRequests(configurers::authorizationRules)

                // UsernamePasswordAuthenticationFilter enabled on POST /login:
                .formLogin(configurers::formLogin)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .httpBasic(AbstractHttpConfigurer::disable)

                .build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Lazy
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}