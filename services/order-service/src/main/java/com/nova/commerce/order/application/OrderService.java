package com.nova.commerce.order.application;

import com.nova.commerce.order.api.dto.CreateOrderRequest;
import com.nova.commerce.order.api.dto.CreateOrderResponse;
import com.nova.commerce.order.api.dto.OrderResponse;
import com.nova.commerce.order.domain.Order;
import com.nova.commerce.order.domain.OrderRepository;
import com.nova.commerce.order.event.*;
import com.nova.commerce.order.api.dto.CreateOrderItemRequest;
import com.nova.commerce.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    // 메서드3-1. 결제 완료 처리
    @Transactional
    public void completePayment(String orderId) {
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

    // 메서드3. 주문 결제 처리
    @Transactional
    public void payOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        PaymentRequestedEvent event = new PaymentRequestedEvent(
                UUID.randomUUID().toString(),
                "PAYMENT_REQUESTED",
                order.getOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                "CARD",
                "1111",
                LocalDateTime.now()
        );

        orderEventPublisher.publish(event);
    }

    // 메서드4-1. 배송 시작 표시 처리
    @Transactional
    public void markAsShipped(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));
        order.ship();
    }

    // 메서드4-2. 배송 완료 표시 처리
    @Transactional
    public void markAsDelivered(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));
        order.deliver();
    }


    // 메서드5. 주문 취소 처리
    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        order.cancel();

        OrderCanceledEvent event = new OrderCanceledEvent(
                UUID.randomUUID().toString(),
                "ORDER_CANCELED",
                order.getOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                LocalDateTime.now()
        );
        orderEventPublisher.publish(event);
    }

    // 메서드6. 반품 요청 처리
    @Transactional
    public void requestReturnOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        order.requestReturn();

        OrderReturnRequestedEvent event = new OrderReturnRequestedEvent(
                UUID.randomUUID().toString(),
                "RETURN_REQUESTED",
                order.getOrderId(),
                order.getUserId(),
                "RETURN_REQUESTED",
                LocalDateTime.now()
        );
        orderEventPublisher.publish(event);

    }

    // 메서드7. 반품 완료 처리
    @Transactional
    public void returnOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        order.returnOrder();
        OrderReturnedEvent event = new OrderReturnedEvent(
                UUID.randomUUID().toString(),
                "RETURNED",
                order.getOrderId(),
                order.getUserId(),
                "RETURNED",
                LocalDateTime.now()
        );
        orderEventPublisher.publish(event);
    }

    // 메서드8. 환불 완료 처리
    @Transactional
    public void refundOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));

        order.refund();
        OrderRefundedEvent event = new OrderRefundedEvent(
                UUID.randomUUID().toString(),
                "REFUNDED",
                order.getOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                LocalDateTime.now()
        );
        orderEventPublisher.publish(event);
    }

    // 메서드9. 주문 조회
    @Transactional(readOnly = true)
    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found. orderId=" + orderId));
        return new OrderResponse(
                order.getOrderId(),
                order.getUserId(),
                order.getStatus(),
                order.getGrossAmount(),
                order.getDiscountAmount(),
                order.getTotalAmount()
        );
    }



}
