package com.nova.commerce.shipping.consumer;

import com.nova.commerce.shipping.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidConsumer {

    @KafkaListener(topics = "nova.order.events",groupId = "shipping-service")
    public void consume(OrderPaidEvent event){
        if(!"ORDER_PAID".equals(event.eventType())){
            return;
        }

        log.info(
                "[SHIPPING] ORDER_PAID received. orderId={}, amount={}",
                event.orderId(),
                event.paidAmount()
        );


    }
}
