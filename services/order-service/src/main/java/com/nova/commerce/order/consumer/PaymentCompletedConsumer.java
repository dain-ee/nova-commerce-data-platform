package com.nova.commerce.order.consumer;

import com.nova.commerce.order.application.OrderService;
import com.nova.commerce.order.event.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "nova.payment.events",groupId = "order-service")
    private void consume(PaymentCompletedEvent event) {
        if (!"PAYMENT_COMPLETED".equals(event.eventType())) {
            return;
        }

        log.info(
                "[ORDER] PAYMENT_COMPLETED received. orderId={}",
                event.orderId()
        );

        orderService.completePayment(event.orderId());
    }
}
