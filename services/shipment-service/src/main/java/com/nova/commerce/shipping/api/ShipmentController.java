package com.nova.commerce.shipping.api;

import com.nova.commerce.shipping.application.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping")
@CrossOrigin(origins = "http://localhost:8080")
public class ShipmentController {

    public final ShipmentService shipmentService;

    // API1) 배송 시작 요청 처리
    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Void> shipOrder(@PathVariable String orderId) {
        shipmentService.shipOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // API2) 배송 완료 요청 처리
    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<Void> deliverOrder(@PathVariable String orderId) {
        shipmentService.deliverOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
