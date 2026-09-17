package com.fitnesstraining.config.test;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;

public interface KafkaTestContainer {

    @Container
    KafkaContainer KAFKA = new KafkaContainer("apache/kafka:4.0.0");
}
