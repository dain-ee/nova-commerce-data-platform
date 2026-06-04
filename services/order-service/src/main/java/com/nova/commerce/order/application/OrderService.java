package com.nova.commerce.order.application;

import com.nova.commerce.order.api.dto.CreateOrderRequest;
import com.nova.commerce.order.api.dto.CreateOrderResponse;
import com.nova.commerce.order.domain.Order;
import com.nova.commerce.order.domain.OrderRepository;
import com.nova.commerce.order.event.OrderCreatedEvent;
import com.nova.commerce.order.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final long DEFAULT_UNIT_PRICE = 29500L;

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        long orderAmount = DEFAULT_UNIT_PRICE * request.quantity();

        Order order =
                Order.create(
                        request.userId(),
                        request.productId(),
                        request.quantity(),
                        orderAmount
                );

        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
      "ORDER_CREATED",
                savedOrder.getOrderId(),
                savedOrder.getUserId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getOrderAmount(),
                LocalDateTime.now()
        );

        orderEventPublisher.publish(event);

        return new CreateOrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name()
        );
    }


}
