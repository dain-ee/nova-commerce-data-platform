package com.nova.commerce.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "channel",nullable = false,length = 30)
    private String channel;

    @Column(name = "coupon_id",length = 50)
    private String couponId;

    @Column(name = "gross_amount",nullable = false)
    private long grossAmount;

    @Column(name = "discount_amount")
    private long discountAmount;

    @Column(name = "total_amount", nullable = false)
    private long totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Order create(String userId, String channel, String couponId, List<OrderItem> items) {
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.orderId = "ord-" + UUID.randomUUID();
        order.userId = userId;
        order.channel = channel;
        order.couponId = couponId;
        order.items.addAll(items);
        order.grossAmount = calculateGrossAmount(items);
        order.discountAmount = calculateDiscountAmount(couponId);
        order.totalAmount = order.grossAmount - order.discountAmount;
        order.status = OrderStatus.CREATED;
        order.createdAt = now;
        order.updatedAt = now;

        return order;
    }

    public static long calculateGrossAmount(List<OrderItem> items) {
        return items.stream()
                .mapToLong(OrderItem::getLineAmount)
                .sum();
    }

    public static long calculateDiscountAmount(String couponId) {
        if (couponId == null || couponId.isBlank()) {
            return 0;
        }
        return 5000;
    }
}
