package com.fitnesstraining.repository.producer;

import com.fitnesstraining.domain.message.CoachWorkload;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachWorkloadProducer {

    private static final String TOPIC = "coach-workload";

    private final KafkaTemplate<String, CoachWorkload> kafkaTemplate;


    // Kafka producer is prepared, but not used
    public void send(CoachWorkload message) {
        kafkaTemplate.send(TOPIC, message);
    }
}