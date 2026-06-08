package com.nova.commerce.payment.event;

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
        long grossAmount,
        long discountAmount,
        long totalAmount,
        LocalDateTime eventTime
) {
}
