package com.nova.commerce.order.api.dto;

public record CreateOrderResponse(
        String orderId,
        String status
) {
}
