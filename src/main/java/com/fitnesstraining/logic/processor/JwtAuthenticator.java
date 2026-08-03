package com.fitnesstraining.logic.processor;

import com.fitnesstraining.config.security.BruteForceProtector;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

import static com.fitnesstraining.utils.SharedStrings.JWT_COOKIE_NAME;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticator {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final BruteForceProtector bruteForceProtector;


    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Duration jwtExpiration;
    @Value("${jwt.response.cookie.secure}")
    private boolean jwtResponseCookieSecure;


    // todo: logs
    public ResponseCookie authenticate(UsernamePasswordAuthenticationRequest request) {

        final UserDetails userDetails;
        final ResponseCookie response;
        final String jwt;


        bruteForceProtector.checkAttempts(request);

        userDetails = repository.findByUsername(request.getUsername());

        if (userDetails == null) {
            bruteForceProtector.blockHost(request.getIp());
            throw new UsernameNotFoundException("User not found");
        }

        if (!encoder.matches(request.getPassword(), userDetails.getPassword())) {
            bruteForceProtector.incrementAttempts(request);
            throw new BadCredentialsException("Invalid credentials");
        }


        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        bruteForceProtector.onSuccessfulLogin(request);

        jwt = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration.toMillis()))
                .signWith(getSigningKey())
                .compact();

        response = ResponseCookie
                .from(JWT_COOKIE_NAME, jwt)
                .httpOnly(true)
                .secure(jwtResponseCookieSecure)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        return response;
    }


    private SecretKey getSigningKey() {

        final byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
