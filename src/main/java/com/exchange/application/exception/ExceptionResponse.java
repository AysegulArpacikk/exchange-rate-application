package com.exchange.application.exception;

import com.exchange.application.type.ResponseStatusType;

import java.util.Date;

public class ExceptionResponse {

    public static final String SOURCE_CURRENCY_CODE_NOT_FOUND = "Source currency code not found!";
    public static final String TARGET_CURRENCY_CODE_NOT_FOUND = "Target currency code not found!";
    public static final String WRONG_DATE_REQUEST = "The start date cannot be later than the end date.";

    private String status;
    private String errorCode;
    private String errorMessage;
    private Date errorTime;

    public ExceptionResponse() {
        this.status = ResponseStatusType.FAILURE.getValue();
    }

    public ExceptionResponse(ResponseStatusType status) {
        this.status = status.getValue();
    }

    public static ExceptionResponse createResultInfo(String message, String errorType) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(message);
        exceptionResponse.setErrorCode(errorType);
        exceptionResponse.setErrorTime(new Date());
        return exceptionResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }
}
