package com.fitnesstraining.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.redis.test.autoconfigure.DataRedisTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataRedisTest
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test() {

        final String input = "Hello";

        redisTemplate.opsForValue().set("test", input);

        final String output = redisTemplate.opsForValue().get("test");

        assertEquals(input, output);
    }
}