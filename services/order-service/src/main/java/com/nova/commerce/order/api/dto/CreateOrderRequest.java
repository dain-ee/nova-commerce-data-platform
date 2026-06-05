package com.nova.commerce.order.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank String userId,
        @NotBlank String channel,
        String couponId,
        @NotEmpty @Valid List<CreateOrderItemRequest> items
        ) {

}