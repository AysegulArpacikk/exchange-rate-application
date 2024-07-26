package com.exchange.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SourceCurrencyCodeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5758415041178005144L;

    public SourceCurrencyCodeNotFoundException(String message) {
        super(message);
    }
}
