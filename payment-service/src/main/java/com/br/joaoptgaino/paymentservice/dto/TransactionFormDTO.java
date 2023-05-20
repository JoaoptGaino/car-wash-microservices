package com.br.joaoptgaino.paymentservice.dto;

import com.br.joaoptgaino.paymentservice.constants.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionFormDTO {
    @NotNull
    private Double transactionValue;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private UUID schedulingId;
}
