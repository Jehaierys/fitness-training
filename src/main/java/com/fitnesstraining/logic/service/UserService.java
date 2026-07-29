package com.fitnesstraining.logic.service;

import com.fitnesstraining.config.security.BruteForceProtector;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.dto.response.JwtAuthenticationResponse;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Lazy
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BruteForceProtector bruteForceProtector;
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Value("${jwt.secret}")
    private static String jwtSecret;


    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Loading user by username: {}", username);
        return repository.findByUsername(username);
    }


    public void changePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        repository.update(user);
        log.info("Password updated for user with id: {}", user.getId());
    }

    @Transactional
    public void setActive(Long id, Boolean active, User principal) {

        if (!principal.getId().equals(id)) {
            // todo: throw proper exception
            throw new RuntimeException("Unauthorized access");
        }

        final User user = repository.findById(id);

        user.setActive(active);
        log.info("User with id: {} set to active: {}", user.getId(), active);
    }

    @Transactional
    public void setActive(String username, Boolean active, User principal) {

        if (principal.getUsername().equals(username)) {
            // todo: throw proper exception
            throw new RuntimeException("Unauthorized access");
        }

        final User user = repository.findByUsername(username);

        user.setActive(active);
        log.info("User with username: {} set to active: {}", user.getUsername(), active);
    }

    public JwtAuthenticationResponse login(UsernamePasswordAuthenticationRequest dto) {

        final UserDetails userDetails = repository.findByUsername(dto.username());

        if (!encoder.matches(dto.password(), userDetails.getPassword())) {
            // todo: Authentication Exception
            throw new RuntimeException("Invalid credentials");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // todo: extract to properties
        final Duration EXPIRATION = Duration.ofHours(1);

        final String jwt = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION.toMillis()))
                .signWith(getSigningKey())
                .compact();

        return new JwtAuthenticationResponse(jwt);
    }

    private SecretKey getSigningKey() {
        // todo: I'll remake it later
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Or else
//    public String login(UsernamePasswordDto dto) {
//
//        // DaoAuthenticationProvider fetches UserDetails and checks password
//        Authentication authentication = authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken.unauthenticated(
//                        dto.username(),
//                        dto.password()
//                )
//        );
//
//        // Make Authentication available for the current request
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        final Duration expiration = Duration.ofHours(1);
//
//        String token = Jwts.builder()
//                .subject(authentication.getName()) // username
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
//                .signWith(getSigningKey())
//                .compact();
//
//        return token;
//    }
}