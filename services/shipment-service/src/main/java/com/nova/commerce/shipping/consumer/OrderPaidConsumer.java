package com.nova.commerce.shipping.consumer;

import com.nova.commerce.shipping.event.OrderPaidEvent;
import com.nova.commerce.shipping.event.OrderShippedEvent;
import com.nova.commerce.shipping.event.ShipmentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidConsumer {

    private final ShipmentEventPublisher shipmentEventPublisher;

    @KafkaListener(topics = "nova.order.events",groupId = "shipping-service")
    public void consume(OrderPaidEvent event){
        if(!"ORDER_PAID".equals(event.eventType())){
            return;
        }

        log.info(
                "[SHIPPING] ORDER_PAID received. orderId={}, amount={}",
                event.orderId(),
                event.paidAmount()
        );

        // 배송 시작 이벤트 생성 및 발행
        OrderShippedEvent orderShippedEvent = new OrderShippedEvent(
                UUID.randomUUID().toString(),
                "ORDER_SHIPPED",
                event.orderId(),
                event.userId(),
                "SHIPPED",
                LocalDateTime.now()
        );

        shipmentEventPublisher.publish(orderShippedEvent);


    }
}
