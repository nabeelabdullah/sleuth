package com.monitoring.actuator.config;


import brave.Span;
import brave.Tracer;
import brave.kafka.clients.KafkaTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.CurrentTraceContext.Scope;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.util.SpanNameUtil;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.reflect.Method;

import static org.springframework.util.ReflectionUtils.findMethod;


class TracingAspect {
    @Autowired
    Tracer tracer;
    @Autowired
    KafkaTracing kafkaTracing;

    // toy example as listeners allow parameters besides record.
    // Real needs to intercept MessagingMessageListenerAdapter.invokeHandler or similar
    @Around("@annotation(listener)")
    public Object traceListener(
            ProceedingJoinPoint pjp,
            KafkaListener listener,
            ConsumerRecord<?, ?> record
    ) throws Throwable {
        String name = SpanNameUtil.toLowerHyphen(getMethod(pjp).getName());
        Span span = kafkaTracing.nextSpan(record).name(name).start();

        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            return pjp.proceed();
        } catch (RuntimeException | Error e) {
            String message = e.getMessage();
            if (message == null) message = e.getClass().getSimpleName();
            span.tag("error", message);
            throw e;
        } finally {
            span.finish();
        }
    }

    static Method getMethod(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        return findMethod(pjp.getTarget().getClass(), method.getName(), method.getParameterTypes());
    }
}