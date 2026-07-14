package com.fitnesstraining.config.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final GrantedAuthority roleCoach = new SimpleGrantedAuthority("ROLE_COACH");
    private final GrantedAuthority roleTrainee = new SimpleGrantedAuthority("ROLE_TRAINEE");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )

                // UsernamePasswordAuthenticationFilter enabled on POST /login:
                .formLogin(form -> form
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
                        })
                )

                .httpBasic(AbstractHttpConfigurer::disable)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}