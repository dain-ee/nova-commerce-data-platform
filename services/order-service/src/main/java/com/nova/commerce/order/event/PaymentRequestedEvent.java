package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record PaymentRequestedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        long totalAmount,
        String paymentMethod,
        String cardLast4,
        LocalDateTime eventTime
) {
}
