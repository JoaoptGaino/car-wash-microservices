package com.br.joaoptgaino.paymentservice.repository;

import com.br.joaoptgaino.paymentservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
