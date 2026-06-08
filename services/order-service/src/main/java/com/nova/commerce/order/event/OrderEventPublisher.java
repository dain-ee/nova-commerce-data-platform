package com.nova.commerce.order.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private static final String ORDER_EVENTS_TOPIC = "nova.order.events";
    private static final String PAYMENT_EVENTS_TOPIC = "nova.payment.events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 메서드1. 주문생성 이벤트 발행
    public void publish(OrderCreatedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드2. 주문결제 이벤트 발행
    public void publish(OrderPaidEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드3. 배송 이벤트 발행
    public void publish(OrderShippedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드4. 배달 완료 이벤트 발행
    public void publish(OrderDeliveredEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드5. 주문 취소 이벤트 발행
    public void publish(OrderCanceledEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드6. 반품 요청 이벤트 발행
    public void publish(OrderReturnRequestedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드7. 반품 완료 이벤트 발행
    public void publish(OrderReturnedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드8. 환불 완료 이벤트 발행
    public void publish(OrderRefundedEvent event){
        kafkaTemplate.send(
                ORDER_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }

    // 메서드9. 결제 요청 이벤트 발행
    public void publish(PaymentRequestedEvent event){
        kafkaTemplate.send(
                PAYMENT_EVENTS_TOPIC,
                event.orderId(),
                event
        );
    }
}

