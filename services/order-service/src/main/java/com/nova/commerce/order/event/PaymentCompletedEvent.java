package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record PaymentCompletedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        long paidAmount,
        LocalDateTime eventTime
) {
}
