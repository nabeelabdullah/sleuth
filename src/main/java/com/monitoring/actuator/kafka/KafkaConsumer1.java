package com.monitoring.actuator.kafka;

import brave.Span;
import brave.Tracer;
import brave.kafka.clients.KafkaTracing;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@EnableAutoConfiguration
@Service
@EnableAspectJAutoProxy
//@Import(KafkaConsumer1.TracingAspect.class)
public class KafkaConsumer1 {

    static Logger logger = LoggerFactory.getLogger(KafkaConsumer1.class);

    private final String topic = "SLEUTH_TOPIC";

    @Autowired
    Tracer tracer;
    @Autowired
    KafkaTracing kafkaTracing;

    @KafkaListener(groupId = "grId", topics = topic)
    public void consume(List<ConsumerRecord<String, String>> record) {
        for (ConsumerRecord<String, String> rs : record) {
            Span span = kafkaTracing.nextSpan(rs).name("kafkaListner" + topic).start();
            Tracer.SpanInScope ws = tracer.withSpanInScope(span);
            try {
                logger.info("consume message " + "" + " " + "" + record.size() + " ");
                Thread.sleep(400);
            } catch (Exception er) {
                er.printStackTrace();
            } finally {
                span.finish();
            }
        }
    }


}
