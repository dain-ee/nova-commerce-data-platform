package com.nova.commerce.order.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.commerce.order.application.OrderService;
import com.nova.commerce.order.event.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderShippedConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "nova.shipment.events",groupId = "order-service")
    public void consume(String message) throws Exception {
        OrderShippedEvent event
                = objectMapper.readValue(message, OrderShippedEvent.class);

        if(!"ORDER_SHIPPED".equals(event.eventType())){
            return;
        }

        log.info(
                "[ORDER] ORDER_SHIPPED received. orderId={}",
                event.orderId()
        );

        orderService.markAsShipped(event.orderId());
    }
}
