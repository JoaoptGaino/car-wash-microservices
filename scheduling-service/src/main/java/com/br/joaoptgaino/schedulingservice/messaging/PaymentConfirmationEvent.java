package com.br.joaoptgaino.schedulingservice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentConfirmationEvent {
    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload Message message) {
        log.info("Header: {}", message.getHeaders().get("payment"));
        log.info("Message: {}", message.getPayload());
    }
}
