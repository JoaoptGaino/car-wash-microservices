package com.br.joaoptgaino.paymentservice.dto;

import com.br.joaoptgaino.paymentservice.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private UUID id;
    private String username;
    private Double transactionValue;
    private TransactionType transactionType;
    private Date createdAt;
    private Date updatedAt;
}
