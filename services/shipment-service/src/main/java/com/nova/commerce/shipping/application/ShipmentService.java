package com.nova.commerce.shipping.application;

import com.nova.commerce.shipping.event.OrderDeliveredEvent;
import com.nova.commerce.shipping.event.OrderShippedEvent;
import com.nova.commerce.shipping.event.ShipmentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentEventPublisher shipmentEventPublisher;

    // 메서드1. 배송 시작 처리
    @Transactional
    public void shipOrder(String orderId) {

        OrderShippedEvent orderShippedEvent = new OrderShippedEvent(
                UUID.randomUUID().toString(),
                "ORDER_SHIPPED",
                orderId,
                null,
                "SHIPPED",
                LocalDateTime.now()
        );

        shipmentEventPublisher.publish(orderShippedEvent);
    }

    // 메서드2. 배송 완료 처리
    @Transactional
    public void deliverOrder(String orderId) {
        OrderDeliveredEvent orderDeliveredEvent = new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                "ORDER_DELIVERED",
                orderId,
                null,
                "DELIVERED",
                LocalDateTime.now()
        );

        shipmentEventPublisher.publish(orderDeliveredEvent);
    }

}
