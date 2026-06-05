package com.nova.commerce.order.event;

import java.time.LocalDateTime;

public record OrderReturnRequestedEvent(
        String eventId,
        String eventType,
        String orderId,
        String userId,
        String returnRequestStatus,
        LocalDateTime eventTime
) {
}
