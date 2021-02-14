package com.swiftqueue.exception.business;

public class BusinessException extends Exception {

    private final String type;
    private final String operation;
    private final String message;

    public BusinessException(String type, String operation, String message) {
        super("Could not " + operation + " " + type + ", " + message);
        this.type = type;
        this.operation = operation;
        this.message = "Could not " + operation + " " + type + ": " + message;
    }

    public String getType() {
        return type;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
