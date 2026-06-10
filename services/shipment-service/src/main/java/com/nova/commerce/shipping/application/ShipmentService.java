package com.nova.commerce.shipping.application;

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

    @Transactional
    public void shipOrder(String orderId) {

        // 메서드1. 배송 시작 처리
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

}
