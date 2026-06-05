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

    // API 1) 주문 생성 요청 처리
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
            CreateOrderResponse response =
                    orderService.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // API 2) 주문 결제 요청 처리
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Void> payOrder(@PathVariable String orderId) {
        orderService.payOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // API 3) 주문 배송 요청 처리
    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Void> shipOrder(@PathVariable String orderId) {
        orderService.shipOrder(orderId);
        return ResponseEntity.noContent().build();
    }


}
