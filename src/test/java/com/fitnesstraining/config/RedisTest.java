package com.fitnesstraining.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisTest {

    private final StringRedisTemplate redisTemplate;

    public void test() {

        redisTemplate.opsForValue().set("test", "hello");

        String value = redisTemplate.opsForValue().get("test");
    }
}