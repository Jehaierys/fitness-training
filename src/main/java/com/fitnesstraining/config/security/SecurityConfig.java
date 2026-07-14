package com.fitnesstraining.config.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.function.Consumer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final GrantedAuthority roleCoach = new SimpleGrantedAuthority("ROLE_COACH");
    private final GrantedAuthority roleTrainee = new SimpleGrantedAuthority("ROLE_TRAINEE");


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizationRules::accept)

                // UsernamePasswordAuthenticationFilter enabled on POST /login:
                .formLogin(formLoginConfiguration::accept)

                .httpBasic(AbstractHttpConfigurer::disable)

                .build();
    }

    private final Consumer<AuthorizeHttpRequestsConfigurer<HttpSecurity>
                .AuthorizationManagerRequestMatcherRegistry> authorizationRules = auth -> auth
            .requestMatchers(whileList()).permitAll()

            // Registration endpoints
            .requestMatchers(
                    HttpMethod.POST,
                    "/trainees",
                    "/coaches"
            ).permitAll()

            // Everything else requires authentication
            .anyRequest().authenticated();

    private String[] whileList() {
        return new String[] {
                // Public static resources
                "/",
                "/index.html",
                "/authentication.html",
                "/registration.html",
                "/css/**",
                "/js/**",
                "/images/**",
                "/favicon.ico",

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

    private final Consumer<FormLoginConfigurer<HttpSecurity>> formLoginConfiguration = form -> form
            .loginPage("/authentication.html")
            .loginProcessingUrl("/login")
            .permitAll()
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler((request, response, authentication) -> {

                Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

                if (roles.contains(roleTrainee)) {
                    response.sendRedirect("/trainee.html");
                } else if (roles.contains(roleCoach)) {
                    response.sendRedirect("/coach.html");
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            });

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) {
        return configuration.getAuthenticationManager();
    }
}