package com.nova.commerce.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private long unitPrice;

    @Column(name = "line_amount", nullable = false)
    private long lineAmount;

    protected OrderItem(String productId, String productName, String category, int quantity, long unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineAmount = unitPrice * quantity;
    }

    public static  OrderItem create(String productId, String productName, String category, int quantity, long unitPrice) {
        return new OrderItem(productId, productName, category, quantity, unitPrice);
    }
}
