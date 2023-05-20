package com.br.joaoptgaino.paymentservice.service;

import com.br.joaoptgaino.paymentservice.dto.TransactionDTO;
import com.br.joaoptgaino.paymentservice.dto.TransactionFormDTO;
import com.br.joaoptgaino.paymentservice.model.Transaction;
import com.br.joaoptgaino.paymentservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AmqpTemplate paymentEventSender;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDTO create(TransactionFormDTO data, String username) {
        Transaction transaction = transactionRepository.save(Transaction.builder().
                transactionType(data.getTransactionType())
                .transactionValue(data.getTransactionValue())
                .schedulingId(data.getSchedulingId())
                .username(username).build());

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("scheduling_payment", transaction.getSchedulingId());
        messageProperties.setHeader("scheduling_payment_value", transaction.getTransactionValue());
        Message message = new Message(data.toString().getBytes(), messageProperties);

        paymentEventSender.convertAndSend("payment", "routing-key-payment", message);
        return modelMapper.map(transaction, TransactionDTO.class);
    }
}
