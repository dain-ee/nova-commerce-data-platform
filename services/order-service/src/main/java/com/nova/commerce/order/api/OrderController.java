package com.nova.commerce.order.api;

import com.nova.commerce.order.api.dto.CreateOrderRequest;
import com.nova.commerce.order.api.dto.CreateOrderResponse;
import com.nova.commerce.order.application.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
            CreateOrderResponse response =
                    orderService.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
