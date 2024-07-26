package com.exchange.application.type;

public enum ResponseStatusType {

    SUCCESS("success"), FAILURE("failure");

    private String value;

    private ResponseStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
