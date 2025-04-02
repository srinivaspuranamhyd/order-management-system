package com.example.ordermanagement.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderNotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        String message = "Custom error message";
        OrderNotFoundException exception = new OrderNotFoundException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithOrderId_ShouldSetFormattedMessage() {
        Long orderId = 123L;
        OrderNotFoundException exception = new OrderNotFoundException(orderId);
        assertEquals("Order not found with id: " + orderId, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithMessageAndCause_ShouldSetBoth() {
        String message = "Custom error message";
        Throwable cause = new RuntimeException("Original cause");
        OrderNotFoundException exception = new OrderNotFoundException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void constructor_WithNullOrderId_ShouldHandleNull() {
        OrderNotFoundException exception = new OrderNotFoundException((Long) null);
        assertEquals("Order not found with id: null", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessage_ShouldHandleNull() {
        OrderNotFoundException exception = new OrderNotFoundException((String) null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessageAndCause_ShouldHandleNull() {
        Throwable cause = new RuntimeException("Original cause");
        OrderNotFoundException exception = new OrderNotFoundException(null, cause);
        assertNull(exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
} 