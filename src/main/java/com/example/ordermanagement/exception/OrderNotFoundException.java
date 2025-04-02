package com.example.ordermanagement.exception;

public class OrderNotFoundException extends RuntimeException {
    
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long orderId) {
        super("Order not found with id: " + orderId);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 