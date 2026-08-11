package com.fitnesstraining.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProcessor {

    private final UserDetailsService userDetailsService;
//    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String token;
    private String username;
    private UserDetails userDetails;
    private Date expiration;


    public synchronized void process(String token, HttpServletRequest request) {
        // todo: is it too big for logs?
        log.info("Processing JWT token: {}", token);

        this.token = token;

        extractClaims();

        if (shouldAuthenticate()) {

            loadUseDetails();

            if (isTokenValid()) {

                authenticate(request);

                log.info("{} authenticated", username);

            }
        }
    }

    private void extractClaims() {

        try {
            final Claims claims =  Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            this.username = claims.getSubject();
            this.expiration = claims.getExpiration();

            log.debug("JWT claims extracted: username - {}", username);

        } catch (JwtException e) {
            onMalformedJwt(e);
        }
    }

    // todo
    private void onMalformedJwt(JwtException exception) {

        SecurityContextHolder.clearContext();
        log.error("Error occurred while parsing JWT token");
        throw exception;
    }

    private void loadUseDetails() {
        userDetails = userDetailsService.loadUserByUsername(username);
        log.debug("Loaded user details for username - {}", username);
    }

    private boolean shouldAuthenticate() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return username != null
                && (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken);
    }

    private boolean isTokenValid() {
        return usernamesMatch() && tokenNotExpired();
    }

    private boolean usernamesMatch() {
        return username.equals(userDetails.getUsername());
    }

    public boolean tokenNotExpired() {
        return expiration.after(new Date());
    }

    private SecretKey getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

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
