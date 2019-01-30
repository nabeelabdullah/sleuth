package com.monitoring.actuator.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "SLEUTH_TOPIC";

    public void send(String key, String value) {
        logger.info("Produce -> " + key + " -> " + value);
        kafkaTemplate.send(topic, value);
    }
}
