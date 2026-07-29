package com.fitnesstraining.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.fitnesstraining.utils.Paths.*;

@Component
public final class Configurers {


    private final GrantedAuthority roleCoach = new SimpleGrantedAuthority("ROLE_COACH");

    private final GrantedAuthority roleTrainee = new SimpleGrantedAuthority("ROLE_TRAINEE");

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;


    void cors(CorsConfigurer<HttpSecurity> request) {

        final CorsConfigurer<HttpSecurity> corsConfigurer = new CorsConfigurer<>();

        corsConfigurer.configurationSource(this::corsConfiguration);

    }

    private CorsConfiguration corsConfiguration(HttpServletRequest request) {

        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of(
                allowedOrigins
        ));

        corsConfiguration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "QUERY"
        ));

        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("Location"));
        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.setMaxAge(3600L); // keep in browser for 1 hour

        return corsConfiguration;
    }


    void authorizationRules(
            AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry auth
    ) {
        authorizationRules.accept(auth);
    }

    private final Consumer<AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry> authorizationRules = auth -> auth
            .requestMatchers(whileList()).permitAll()

            // Registration endpoints
            .requestMatchers(
                    HttpMethod.POST,
                    "/**/trainees",
                    "/**/coaches"
            ).permitAll()

            // Actuator
            .requestMatchers("/actuator/health/**").permitAll()
            // todo: restrict
            .requestMatchers("/actuator/prometheus").permitAll()
            .requestMatchers("/actuator/**").hasRole("ADMIN")

            // Everything else requires authentication
            .anyRequest().authenticated();


    private String[] whileList() {
        return new String[] {

                // Public static resources
                "/",
                "/index.html",
                AUTHENTICATION_PAGE_URL,
                REGISTRATION_PAGE_URL,
                "/css/**",
                "/js/**",
                "/images/**",
                "/favicon.svg",

                // Swagger
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",

                // Spring Security login
                "/login",
                "/logout"
        };
    }
}
