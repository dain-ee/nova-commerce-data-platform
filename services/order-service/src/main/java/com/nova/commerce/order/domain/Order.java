package com.nova.commerce.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "order_amount", nullable = false)
    private long orderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Order create(String userId, String productId, int quantity, long orderAmount) {
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.orderId = "ord-" + UUID.randomUUID();
        order.userId = userId;
        order.productId = productId;
        order.quantity = quantity;
        order.orderAmount = orderAmount;
        order.status = OrderStatus.CREATED;
        order.createdAt = now;
        order.updatedAt = now;

        return order;
    }
}
