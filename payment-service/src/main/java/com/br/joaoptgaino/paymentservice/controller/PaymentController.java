package com.br.joaoptgaino.paymentservice.controller;

import com.br.joaoptgaino.paymentservice.messaging.PaymentEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final AmqpTemplate paymentEventSender;

    @PostMapping
    public String send(@RequestBody String payload) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("payment", payload);
        Message message = new Message(payload.getBytes(), messageProperties);
        paymentEventSender.convertAndSend("payment", "routing-key-payment", message);
        return String.format("Done %s", payload);
    }
}
