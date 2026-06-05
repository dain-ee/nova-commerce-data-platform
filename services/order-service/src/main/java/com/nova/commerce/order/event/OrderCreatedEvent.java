package com.nova.commerce.order.event;

import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        String channel,
        String couponId,
        List<OrderItemPayload> items,
        long totalAmount,
        LocalDateTime eventTime
) {
}
