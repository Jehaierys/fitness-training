package com.fitnesstraining.config.application;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String COACH_WORKLOAD_TOPIC = "coach-workload";

    @Bean
    public NewTopic coachWorkloadTopic() {
        return TopicBuilder.name(COACH_WORKLOAD_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}