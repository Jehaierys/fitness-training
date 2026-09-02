package com.fitnesstraining.config.application;

import com.fitnesstraining.domain.message.CoachWorkload;
import com.fitnesstraining.repository.producer.CoachWorkloadProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


// This Application Listener is for Kafka demonstration only
// I'll delete it later when I write more suitable messaging tests
// In Kafka UI, we can see message flow
// GPT made this class, but I'm absolutely aware of what happens here
@Slf4j
@Component
@RequiredArgsConstructor
public class CoachWorkloadTestListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CoachWorkloadProducer producer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("shalom");
        log.info("shalom");

        final Thread thread = new Thread(() -> {

            log.info("shalom");

            sleep(5_000);

            producer.send(new CoachWorkload(1, 2026, 120, 1L));

            sleep(100);
            producer.send(new CoachWorkload(2, 2026, 90, 1L));

            sleep(100);
            producer.send(new CoachWorkload(1, 2026, 180, 2L));

            sleep(100);
            producer.send(new CoachWorkload(3, 2026, -60, 2L));

            sleep(100);
            producer.send(new CoachWorkload(8, 2026, 240, 3L));

            log.info(" --------------------------------");
            log.info("");
            log.info("CoachWorkloadTestListener: Sent 5 messages to Kafka topic 'coach-workload' for testing purposes.");
            log.info("");
            log.info(" --------------------------------");
        });

        thread.start();
    }

    private void sleep(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}