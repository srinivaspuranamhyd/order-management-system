package com.example.ordermanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItem orderItem;
    private Order order;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        order = new Order();
    }

    @Test
    void setAndGetMethods_ShouldWorkCorrectly() {
        orderItem.setId(1L);
        orderItem.setProductName("Test Product");
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
        orderItem.setOrder(order);

        assertEquals(1L, orderItem.getId());
        assertEquals("Test Product", orderItem.getProductName());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(10.0, orderItem.getPrice());
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    void setOrder_ShouldUpdateOrderReference() {
        orderItem.setOrder(order);
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    void setOrder_WithNull_ShouldClearOrderReference() {
        orderItem.setOrder(order);
        orderItem.setOrder(null);
        assertNull(orderItem.getOrder());
    }

    @Test
    void getSubtotal_ShouldCalculateCorrectAmount() {
        orderItem.setQuantity(3);
        orderItem.setPrice(10.0);
        assertEquals(30.0, orderItem.getSubtotal());
    }

    @Test
    void getSubtotal_WithZeroQuantity_ShouldReturnZero() {
        orderItem.setQuantity(0);
        orderItem.setPrice(10.0);
        assertEquals(0.0, orderItem.getSubtotal());
    }

    @Test
    void getSubtotal_WithZeroPrice_ShouldReturnZero() {
        orderItem.setQuantity(3);
        orderItem.setPrice(0.0);
        assertEquals(0.0, orderItem.getSubtotal());
    }

    @Test
    void equals_WithSameObject_ShouldReturnTrue() {
        assertTrue(orderItem.equals(orderItem));
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        assertFalse(orderItem.equals(null));
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        assertFalse(orderItem.equals(new Object()));
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        OrderItem anotherItem = new OrderItem();
        orderItem.setId(1L);
        anotherItem.setId(1L);
        assertTrue(orderItem.equals(anotherItem));
    }

    @Test
    void equals_WithDifferentId_ShouldReturnFalse() {
        OrderItem anotherItem = new OrderItem();
        orderItem.setId(1L);
        anotherItem.setId(2L);
        assertFalse(orderItem.equals(anotherItem));
    }

    @Test
    void hashCode_WithSameId_ShouldBeEqual() {
        OrderItem anotherItem = new OrderItem();
        orderItem.setId(1L);
        anotherItem.setId(1L);
        assertEquals(orderItem.hashCode(), anotherItem.hashCode());
    }

    @Test
    void hashCode_WithDifferentId_ShouldBeDifferent() {
        OrderItem anotherItem = new OrderItem();
        orderItem.setId(1L);
        anotherItem.setId(2L);
        assertNotEquals(orderItem.hashCode(), anotherItem.hashCode());
    }
} 