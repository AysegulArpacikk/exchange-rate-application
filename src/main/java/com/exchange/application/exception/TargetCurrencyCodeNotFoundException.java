package com.exchange.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TargetCurrencyCodeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6063151568443341422L;

    public TargetCurrencyCodeNotFoundException(String message) {
        super(message);
    }
}
