package com.fitnesstraining.logic.processor;

import com.fitnesstraining.config.security.BruteForceProtector;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.dto.response.JwtAuthenticationResponse;
import com.fitnesstraining.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticator {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Duration jwtExpiration;

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final BruteForceProtector bruteForceProtector;

    private UsernamePasswordAuthenticationRequest request;
    private UserDetails userDetails;
    private JwtAuthenticationResponse response;

    public synchronized JwtAuthenticationResponse authenticate(UsernamePasswordAuthenticationRequest request) {

        this.request = request;

        loadUserDetails();

        checkCredentials();

        authenticate();

        buildResponse();

        return response;
    }

    private void loadUserDetails() {
        userDetails = repository.findByUsername(request.username());

        if (userDetails == null) {
            // todo: call brute force protector
        }
    }

    private void checkCredentials() {
        if (!encoder.matches(request.password(), userDetails.getPassword())) {
            // todo: call brute force protector
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    private void authenticate() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void buildResponse() {
        final String jwt = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration.toMillis()))
                .signWith(getSigningKey())
                .compact();

        this.response = new JwtAuthenticationResponse(jwt);
    }


    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
