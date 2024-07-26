package com.exchange.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateRequestException extends RestClientException {

    private static final long serialVersionUID = 1319540002031766222L;

    public InvalidDateRequestException(String message) {
        super(message);
    }
}
