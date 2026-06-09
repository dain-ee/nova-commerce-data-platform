package com.nova.commerce.shipping.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentEventPublisher {

    private static final String SHIPMENT_EVENT_TOPIC = "nova.shipment.events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 메서드1. 배송시작 이벤트 발행
    public void publish(OrderShippedEvent event){
        kafkaTemplate.send(
                SHIPMENT_EVENT_TOPIC,
                event.orderId(),
                event
        );
    }
}
