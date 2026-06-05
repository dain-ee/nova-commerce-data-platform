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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

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
                savedOrder.getTotalAmount(),
                LocalDateTime.now()
        );

        orderEventPublisher.publish(event);

        return new CreateOrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name()
        );
    }

    private OrderItem toOrderItem(CreateOrderItemRequest itemRequest) {
        return OrderItem.create(
                itemRequest.productId(),
                itemRequest.productName(),
                itemRequest.category(),
                itemRequest.quantity(),
                itemRequest.unitPrice()
        );
    }


}
