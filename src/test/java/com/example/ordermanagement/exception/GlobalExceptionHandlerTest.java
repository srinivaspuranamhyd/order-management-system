package com.example.ordermanagement.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleOrderNotFoundException_ShouldReturnNotFoundResponse() {
        Long orderId = 123L;
        OrderNotFoundException ex = new OrderNotFoundException(orderId);
        ResponseEntity<Map<String, Object>> response = handler.handleOrderNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("Order not found with id: " + orderId, body.get("message"));
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleIllegalStateException_ShouldReturnBadRequestResponse() {
        String message = "Only PENDING orders can be cancelled";
        IllegalStateException ex = new IllegalStateException(message, null);
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalStateException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(message, body.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerErrorResponse() {
        Exception ex = new RuntimeException("Unexpected error");
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("An unexpected error occurred", body.get("message"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleOrderNotFoundException_WithNullMessage_ShouldHandleNull() {
        OrderNotFoundException ex = new OrderNotFoundException((String) null);
        ResponseEntity<Map<String, Object>> response = handler.handleOrderNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertNull(body.get("message"));
    }

    @Test
    void handleIllegalStateException_WithNullMessage_ShouldHandleNull() {
        IllegalStateException ex = new IllegalStateException((String) null, null);
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalStateException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertNull(body.get("message"));
    }
} 