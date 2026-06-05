package com.nova.commerce.order.event;

public record OrderItemPayload(
        String productId,
        String productName,
        String category,
        int quantity,
        long unitPrice,
        long lineAmount
) {
}
