package com.br.joaoptgaino.paymentservice.controller;

import com.br.joaoptgaino.paymentservice.dto.TransactionDTO;
import com.br.joaoptgaino.paymentservice.dto.TransactionFormDTO;
import com.br.joaoptgaino.paymentservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> send(@RequestBody TransactionFormDTO data, @RequestParam("username") String username) {
        TransactionDTO transactionDTO = transactionService.create(data, username);
        return ResponseEntity.ok(transactionDTO);
    }
}
