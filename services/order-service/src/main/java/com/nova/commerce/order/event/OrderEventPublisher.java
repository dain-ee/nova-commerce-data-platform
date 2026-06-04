package com.nova.commerce.order.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private static final String ORDER_EVENTS_TOPIC = "nova.order.events";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publish(OrderCreatedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }
}

