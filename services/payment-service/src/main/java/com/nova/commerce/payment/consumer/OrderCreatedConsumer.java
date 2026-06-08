package com.nova.commerce.payment.consumer;

import com.nova.commerce.payment.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreatedConsumer {

    @KafkaListener(topics = "nova.order.events",groupId = "payment-service")
    public void consume(OrderCreatedEvent event) {
        if (!"ORDER_CREATED".equals(event.eventType())) {
            return;
        }

        log.info(
                "[PAYMENT] ORDER_CREATED received. orderId={}, userId={}, amount={}",
                event.orderId(),
                event.userId(),
                event.totalAmount()
        );
    }
}
