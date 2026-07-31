package com.fitnesstraining.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.fitnesstraining.utils.SharedStrings.JWT_COOKIE_NAME;


@Lazy
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProcessor processor;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String token = findJwt(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        processor.process(token, request);

        filterChain.doFilter(request, response);
    }

    private String findJwt(HttpServletRequest request) {

        final Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}