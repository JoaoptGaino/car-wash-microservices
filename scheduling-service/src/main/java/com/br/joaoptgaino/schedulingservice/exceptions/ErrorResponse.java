package com.br.joaoptgaino.schedulingservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;
    private List<String> errors;
}