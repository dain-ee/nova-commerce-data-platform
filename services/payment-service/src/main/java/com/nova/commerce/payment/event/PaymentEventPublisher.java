package com.nova.commerce.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private static final String PAYMENT_EVENTS_TOPIC = "nova.payment.events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 메서드1. 결제완료 이벤트 발행
    public void publish(PaymentCompletedEvent event) {
        kafkaTemplate.send(
                PAYMENT_EVENTS_TOPIC,
                event.orderId(),
                event);
    }
}
