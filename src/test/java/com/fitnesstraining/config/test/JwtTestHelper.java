package com.fitnesstraining.config.test;

import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.service.utils.JwtAuthenticator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseCookie;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTestHelper {

    @Value("${jwt.secret}")
    private String jwtSecret;


    private final JwtAuthenticator jwtAuthenticator;

    public Cookie createAuthenticationCookie(
            User user,
            String password
    ) {
        UsernamePasswordAuthenticationRequest request =
                new UsernamePasswordAuthenticationRequest(
                        user.getUsername(),
                        password,
                        "127.0.0.1"
                );

        ResponseCookie responseCookie =
                jwtAuthenticator.authenticate(request);

        return new Cookie(
                responseCookie.getName(),
                responseCookie.getValue()
        );
    }

    public Cookie createAuthenticationCookie(String username) {
        final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        final String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                .claim("roles", List.of("ROLE_TRAINEE"))
                .signWith(key)
                .compact();
        return new Cookie("jwt", token);
    }
}
