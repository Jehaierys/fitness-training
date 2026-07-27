package com.fitnesstraining.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Lazy
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final JwtProcessor processor;

    private String header;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        this.header = request.getHeader(AUTHORIZATION);

        if (shouldNotContinue(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(PREFIX.length());

        processor.process(token, request);

        filterChain.doFilter(request, response);
    }

    private boolean shouldNotContinue(HttpServletRequest request, HttpServletResponse response) {
        return header == null || !header.startsWith(PREFIX);
    }
}