package com.example.ordermanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void enumValues_ShouldBeInCorrectOrder() {
        OrderStatus[] values = OrderStatus.values();
        assertEquals(5, values.length);
        assertEquals(OrderStatus.PENDING, values[0]);
        assertEquals(OrderStatus.PROCESSING, values[1]);
        assertEquals(OrderStatus.SHIPPED, values[2]);
        assertEquals(OrderStatus.DELIVERED, values[3]);
        assertEquals(OrderStatus.CANCELLED, values[4]);
    }

    @Test
    void valueOf_ShouldReturnCorrectEnum() {
        assertEquals(OrderStatus.PENDING, OrderStatus.valueOf("PENDING"));
        assertEquals(OrderStatus.PROCESSING, OrderStatus.valueOf("PROCESSING"));
        assertEquals(OrderStatus.SHIPPED, OrderStatus.valueOf("SHIPPED"));
        assertEquals(OrderStatus.DELIVERED, OrderStatus.valueOf("DELIVERED"));
        assertEquals(OrderStatus.CANCELLED, OrderStatus.valueOf("CANCELLED"));
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.valueOf("INVALID_STATUS"));
    }

    @Test
    void toString_ShouldReturnCorrectString() {
        assertEquals("PENDING", OrderStatus.PENDING.toString());
        assertEquals("PROCESSING", OrderStatus.PROCESSING.toString());
        assertEquals("SHIPPED", OrderStatus.SHIPPED.toString());
        assertEquals("DELIVERED", OrderStatus.DELIVERED.toString());
        assertEquals("CANCELLED", OrderStatus.CANCELLED.toString());
    }

    @Test
    void ordinal_ShouldReturnCorrectPosition() {
        assertEquals(0, OrderStatus.PENDING.ordinal());
        assertEquals(1, OrderStatus.PROCESSING.ordinal());
        assertEquals(2, OrderStatus.SHIPPED.ordinal());
        assertEquals(3, OrderStatus.DELIVERED.ordinal());
        assertEquals(4, OrderStatus.CANCELLED.ordinal());
    }
} 