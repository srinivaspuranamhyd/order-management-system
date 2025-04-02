package com.example.ordermanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        order = new Order();
        orderItem = new OrderItem();
        orderItem.setProductName("Test Product");
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
    }

    @Test
    void addItem_ShouldAddItemAndSetOrderReference() {
        order.addItem(orderItem);

        assertTrue(order.getItems().contains(orderItem));
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    void removeItem_ShouldRemoveItemAndClearOrderReference() {
        order.addItem(orderItem);
        order.removeItem(orderItem);

        assertFalse(order.getItems().contains(orderItem));
        assertNull(orderItem.getOrder());
    }

    @Test
    void getTotalAmount_ShouldCalculateCorrectTotal() {
        order.addItem(orderItem);
        OrderItem secondItem = new OrderItem();
        secondItem.setProductName("Second Product");
        secondItem.setQuantity(3);
        secondItem.setPrice(5.0);
        order.addItem(secondItem);

        double expectedTotal = (orderItem.getPrice() * orderItem.getQuantity()) +
                             (secondItem.getPrice() * secondItem.getQuantity());
        assertEquals(expectedTotal, order.getTotalAmount());
    }

    @Test
    void getTotalAmount_WithEmptyItems_ShouldReturnZero() {
        assertEquals(0.0, order.getTotalAmount());
    }

    @Test
    void setAndGetMethods_ShouldWorkCorrectly() {
        order.setId(1L);
        order.setCustomerName("Test Customer");
        order.setCustomerEmail("test@example.com");
        order.setStatus(OrderStatus.PENDING);
        order.setItems(new ArrayList<>());

        assertEquals(1L, order.getId());
        assertEquals("Test Customer", order.getCustomerName());
        assertEquals("test@example.com", order.getCustomerEmail());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }
} 