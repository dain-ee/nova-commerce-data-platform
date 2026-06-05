package com.nova.commerce.order.application;

import com.nova.commerce.order.api.dto.CreateOrderRequest;
import com.nova.commerce.order.api.dto.CreateOrderResponse;
import com.nova.commerce.order.domain.Order;
import com.nova.commerce.order.domain.OrderRepository;
import com.nova.commerce.order.event.OrderCreatedEvent;
import com.nova.commerce.order.event.OrderEventPublisher;
import com.nova.commerce.order.api.dto.CreateOrderItemRequest;
import com.nova.commerce.order.domain.OrderItem;
import com.nova.commerce.order.event.OrderItemPayload;
import com.nova.commerce.order.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    // 메서드1. 주문 생성
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        // DTO를 도메인으로 변환
        List<OrderItem> orderItems = request.items().stream()
                .map(this::toOrderItem)
                .toList();

        Order order = Order.create(
                        request.userId(),
                        request.channel(),
                        request.couponId(),
                        orderItems
                );

        Order savedOrder = orderRepository.save(order);

        List<OrderItemPayload> itemPayloads =
                savedOrder.getItems().stream()
                        .map(item -> new OrderItemPayload(
                                item.getProductId(),
                                item.getProductName(),
                                item.getCategory(),
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getLineAmount()
                        ))
                        .toList();

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
      "ORDER_CREATED",
                savedOrder.getOrderId(),
                savedOrder.getUserId(),
                savedOrder.getChannel(),
                savedOrder.getCouponId(),
                itemPayloads,
                savedOrder.getGrossAmount(),
                savedOrder.getDiscountAmount(),
                savedOrder.getTotalAmount(),
                LocalDateTime.now()
        );

        orderEventPublisher.publish(event);

        return new CreateOrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name()
        );
    }

    // 메서드2. 아이템주문 DTO를 도메인으로 변경
    private OrderItem toOrderItem(CreateOrderItemRequest itemRequest) {
        return OrderItem.create(
                itemRequest.productId(),
                itemRequest.productName(),
                itemRequest.category(),
                itemRequest.quantity(),
                itemRequest.unitPrice()
        );
    }

    // 메서드3. 주문 결제
    @Transactional
    public void payOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        order.pay();

        OrderPaidEvent event = new OrderPaidEvent(
                UUID.randomUUID().toString(),
                "ORDER_PAID",
                order.getOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                LocalDateTime.now()
        );
        orderEventPublisher.publish(event);
    }


}
