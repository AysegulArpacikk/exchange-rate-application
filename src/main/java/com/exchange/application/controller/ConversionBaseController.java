package com.exchange.application.controller;

import com.exchange.application.exception.ExceptionResponse;
import com.exchange.application.exception.InvalidDateRequestException;
import com.exchange.application.exception.SourceCurrencyCodeNotFoundException;
import com.exchange.application.exception.TargetCurrencyCodeNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ConversionBaseController {

    private static final String CLIENT_ERROR = "clientError";
    private static final String SYSTEM_ERROR = "systemError";

    public ExceptionResponse setExceptionMessage(Exception exception) {
        ExceptionResponse exceptionResponse;

        if (exception instanceof SourceCurrencyCodeNotFoundException
                || exception instanceof TargetCurrencyCodeNotFoundException
                || exception instanceof InvalidDateRequestException) {
            exceptionResponse = ExceptionResponse.createResultInfo(exception.getMessage(), CLIENT_ERROR);
        } else {
            exceptionResponse = handleSystemException(exception);
        }
        return exceptionResponse;
    }

    private ExceptionResponse handleSystemException(Exception exception) {
        return ExceptionResponse.createResultInfo(exception.getMessage(), SYSTEM_ERROR);
    }
}
