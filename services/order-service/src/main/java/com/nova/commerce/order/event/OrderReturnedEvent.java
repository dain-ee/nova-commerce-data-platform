package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record OrderReturnedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        String returnStatus,
        LocalDateTime eventTime
) {
}
