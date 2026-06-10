package com.nova.commerce.order.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.commerce.order.application.OrderService;
import com.nova.commerce.order.event.OrderDeliveredEvent;
import com.nova.commerce.order.event.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "nova.shipment.events", groupId = "order-service")
    public void consume(String message) throws Exception {
        JsonNode root = objectMapper.readTree(message);
        String eventType = root.get("eventType").asText();

        // 배송 시작 처리
        if ("ORDER_SHIPPED".equals(eventType)) {
            OrderShippedEvent event =
                    objectMapper.readValue(message, OrderShippedEvent.class);

            log.info("[ORDER] ORDER_SHIPPED received. orderId={}", event.orderId());
            orderService.markAsShipped(event.orderId());
            return;
        }

        // 배송 완료 처리
        if ("ORDER_DELIVERED".equals(eventType)) {
            OrderDeliveredEvent event =
                    objectMapper.readValue(message, OrderDeliveredEvent.class);

            log.info("[ORDER] ORDER_DELIVERED received. orderId={}", event.orderId());
            orderService.markAsDelivered(event.orderId());
        }
    }
}