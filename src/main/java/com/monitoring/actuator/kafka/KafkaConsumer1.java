package com.monitoring.actuator.kafka;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String topic = "SLEUTH_TOPIC";

    @Autowired
    private Tracer tracer;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @KafkaListener(groupId = "grId", topics = topic)
    public void consume(@Headers MessageHeaders headers, String value) {
        try {
            logger.info("consume message " + value + " " + headers);
            Thread.sleep(400);
        } catch (Exception er) {
        }
    }
}
