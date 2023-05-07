package com.br.joaoptgaino.schedulingservice.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BusinessException extends RuntimeException{
    private final Integer httpStatus;
    private final String message;
    private final List<String> errors;
}
