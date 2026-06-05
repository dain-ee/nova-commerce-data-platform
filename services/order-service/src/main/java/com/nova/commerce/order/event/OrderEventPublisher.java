package com.nova.commerce.order.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private static final String ORDER_EVENTS_TOPIC = "nova.order.events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 메서드1. 주문생성 이벤트 발행
    public void publish(OrderCreatedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드2. 주문결제 이벤트 발행
    public void publish(OrderPaidEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }
}

