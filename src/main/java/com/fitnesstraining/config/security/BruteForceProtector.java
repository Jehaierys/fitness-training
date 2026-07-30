package com.fitnesstraining.config.security;


import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BruteForceProtector {

    private static final int MAX_ATTEMPTS = 3;

    private static final Duration BLOCK_TIME = Duration.ofMinutes(5);

    private static final String AUTHENTICATION_ATTEMPTS_BY_IP = "auth:att:ip:";

    private static final String AUTHENTICATION_ATTEMPTS_BY_USERNAME = "auth:att:unm:";

    private static final String FAIL_MESSAGE = "Too many failed login attempts. Please try again later.";


    private final StringRedisTemplate redis;


    public void checkAttempts(UsernamePasswordAuthenticationRequest request) {

        checkAttemptsByIp(request.getIp());
        checkAttemptsByUsername(request.getUsername());
    }

    // ip belongs to authentication request sender
    private void checkAttemptsByIp(String ip) {

        final int attempts = Optional
                .ofNullable(redis.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_IP + ip))
                .map(Integer::parseInt)
                .orElse(0);

        if (attempts >= MAX_ATTEMPTS) {
            throw new RuntimeException(FAIL_MESSAGE);
        }
    }

    // username belongs to the account that tries to be signed in
    private void checkAttemptsByUsername(String username) {

        final int attempts = Optional
                .ofNullable(redis.opsForValue().get(AUTHENTICATION_ATTEMPTS_BY_USERNAME + username))
                .map(Integer::parseInt)
                .orElse(0);

        if (attempts >= MAX_ATTEMPTS) {
            throw new RuntimeException(FAIL_MESSAGE);
        }
    }


    public void incrementAttempts(UsernamePasswordAuthenticationRequest request) {

        final String ipRecordKey = AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp();
        final String usernameRecordKey = AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername();

        redis.opsForValue().increment(ipRecordKey);
        redis.opsForValue().increment(usernameRecordKey);

        redis.expire(ipRecordKey, BLOCK_TIME);
        redis.expire(usernameRecordKey, BLOCK_TIME);

        log.warn("Failed login attempt for username: {} from IP: {}",
                request.getUsername(),
                request.getIp());
    }

    public void blockHost(String ip) {

        final String ipRecordKey = AUTHENTICATION_ATTEMPTS_BY_IP + ip;

        redis.opsForValue().set(ipRecordKey, ip, BLOCK_TIME);
    }


    public void onSuccessfulLogin(UsernamePasswordAuthenticationRequest request) {

        redis.delete(AUTHENTICATION_ATTEMPTS_BY_IP + request.getIp());
        redis.delete(AUTHENTICATION_ATTEMPTS_BY_USERNAME + request.getUsername());
    }
}