package com.nova.commerce.payment.event;

public record OrderItemPayload(
        String productId,
        String productName,
        String category,
        int quantity,
        long unitPrice,
        long lineAmount
) {
}
