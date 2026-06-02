package com.nova.commerce.order.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequest {
    @NotBlank String userId;
    @NotBlank String productId;
    @Min(1) int quantity;
}
