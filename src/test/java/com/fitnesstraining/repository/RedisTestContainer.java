package com.fitnesstraining.repository;


import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import com.redis.testcontainers.RedisContainer;

public interface RedisTestContainer {

    @Container
    RedisContainer REDIS = new RedisContainer(DockerImageName.parse("redis:7-alpine"));
}