package com.br.joaoptgaino.paymentservice.service;

import com.br.joaoptgaino.paymentservice.dto.TransactionDTO;
import com.br.joaoptgaino.paymentservice.dto.TransactionFormDTO;

public interface TransactionService {
    TransactionDTO create(TransactionFormDTO data, String username);
}
