package com.nova.commerce.payment.consumer;

import com.nova.commerce.payment.event.PaymentCompletedEvent;
import com.nova.commerce.payment.event.PaymentEventPublisher;
import com.nova.commerce.payment.event.PaymentRequestedEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final PaymentEventPublisher paymentEventPublisher;

    @KafkaListener(topics = "nova.payment.events",groupId = "payment-service")
    public void consume(PaymentRequestedEvent event) {
        if (!"PAYMENT_REQUESTED".equals(event.eventType())) {
            return;
        }

        log.info(
                "[PAYMENT] PAYMENT_REQUESTED received. orderId={}, userId={}, amount={}",
                event.orderId(),
                event.userId(),
                event.totalAmount()
        );

        // 결제 완료 이벤트 생성 및 발행
        PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent(
                UUID.randomUUID().toString(),
                "PAYMENT_COMPLETED",
                event.orderId(),
                event.userId(),
                event.totalAmount(),
                LocalDateTime.now()
        );

        paymentEventPublisher.publish(paymentCompletedEvent);

    }
}
