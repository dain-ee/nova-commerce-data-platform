package com.nova.commerce.order.api.dto;

import com.nova.commerce.order.domain.OrderStatus;

public record OrderResponse(
        String orderId,
        String userId,
        OrderStatus status,
        long grossAmount,
        long discountAmount,
        long totalAmount
) {
}
