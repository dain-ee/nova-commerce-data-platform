package com.nova.commerce.order.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
public record CreateOrderItemRequest(
        @NotBlank String productId,
        @NotBlank String productName,
        @NotBlank String category,
        @Min(1) int quantity,
        @Min(1) long unitPrice
) {
}
