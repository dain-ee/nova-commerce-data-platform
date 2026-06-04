package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record OrderCreatedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        String productId,
        int quantity,
        long orderAmount,
        LocalDateTime eventTime
) {
}
