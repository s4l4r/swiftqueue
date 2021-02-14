package com.swiftqueue.exception.server;

public class ResourceNotFoundException extends Exception {

    private final String message;
    private final long id;
    private final String type;

    public ResourceNotFoundException(String type, Long id) {
        super("No " + type + " found with ID: " + id);
        this.message = "No " + type + " found with ID: " + id;
        this.id = id;
        this.type = type;
    }

    public ResourceNotFoundException(String type, String identifier) {
        super("No " + type + " found with identifier " + identifier);
        this.message = "No " + type + " found with identifier " + identifier;
        this.id = 0;
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
