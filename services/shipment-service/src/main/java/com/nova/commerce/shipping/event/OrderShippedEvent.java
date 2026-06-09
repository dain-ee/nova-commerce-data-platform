package com.nova.commerce.shipping.event;

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
