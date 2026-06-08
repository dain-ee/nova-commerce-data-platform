package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record OrderRefundedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        long refundAmount,
        LocalDateTime eventTime
) {
}
