package com.fitnesstraining.config.application;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ContextCounter implements ApplicationListener<ContextRefreshedEvent> {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        int count = COUNTER.incrementAndGet();

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
        System.out.println("ApplicationContext #" + count);
        System.out.println(event.getApplicationContext().getId());
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
    }
}