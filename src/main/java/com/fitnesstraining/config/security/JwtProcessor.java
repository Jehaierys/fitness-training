package com.fitnesstraining.config.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProcessor {

    private final UserDetailsService userDetailsService;
    private final String jwtSecret;

    private String token;
    private String username;
    private UserDetails userDetails;
    private Date expiration;


    public JwtProcessor(
            @Autowired UserDetailsService userDetailsService,
            @Autowired Dotenv dotenv
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtSecret = dotenv.get("JWT_SECRET");
    }


    public synchronized void process(String token, HttpServletRequest request) {
        // todo: is it too big for logs?
        log.info("Processing JWT token: {}", token);

        this.token = token;

        extractClaims();

        if (shouldAuthenticate()) {

            loadUSerDetails();

            if (isTokenValid()) {

                authenticate(request);

                log.info("{} authenticated", username);

            }
        } else {
            // todo: throw anything
        }
    }

    private void extractClaims() {
        final Claims claims =  Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        this.username = claims.getSubject();
        this.expiration = claims.getExpiration();
    }

    private void loadUSerDetails() {
        userDetails = userDetailsService.loadUserByUsername(username);
    }

    private boolean shouldAuthenticate() {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private boolean isTokenValid() {
        return usernamesMatch() && tokenNotExpired();
    }

    private boolean usernamesMatch() {
        return username.equals(userDetails.getUsername());
    }

    public boolean tokenNotExpired() {
        return expiration.before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private void authenticate(HttpServletRequest request) {

        final UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
