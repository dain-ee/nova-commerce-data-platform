package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record OrderShippedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        String shipmentStatus,
        LocalDateTime eventTime
) {
}
