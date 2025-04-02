package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.OrderItem;
import com.example.ordermanagement.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order testOrder;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();

        testOrderItem = new OrderItem();
        testOrderItem.setProductName("Test Product");
        testOrderItem.setQuantity(2);
        testOrderItem.setPrice(10.0);

        testOrder = new Order();
        testOrder.setCustomerName("Test Customer");
        testOrder.setCustomerEmail("test@example.com");
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.addItem(testOrderItem);
    }

    @Test
    void saveOrder_ShouldPersistOrderAndItems() {
        Order savedOrder = orderRepository.save(testOrder);

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getItems());
        assertEquals(1, savedOrder.getItems().size());
        assertNotNull(savedOrder.getItems().get(0).getId());
    }

    @Test
    void findById_WhenOrderExists_ShouldReturnOrder() {
        Order savedOrder = orderRepository.save(testOrder);

        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        assertNotNull(foundOrder);
        assertEquals(savedOrder.getId(), foundOrder.getId());
        assertEquals(savedOrder.getCustomerName(), foundOrder.getCustomerName());
        assertEquals(savedOrder.getStatus(), foundOrder.getStatus());
    }

    @Test
    void findByStatus_ShouldReturnOrdersWithMatchingStatus() {
        Order savedOrder = orderRepository.save(testOrder);
        Order processingOrder = new Order();
        processingOrder.setCustomerName("Another Customer");
        processingOrder.setCustomerEmail("another@example.com");
        processingOrder.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(processingOrder);

        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);

        assertEquals(1, pendingOrders.size());
        assertEquals(savedOrder.getId(), pendingOrders.get(0).getId());
        assertEquals(OrderStatus.PENDING, pendingOrders.get(0).getStatus());
    }

    @Test
    void deleteOrder_ShouldDeleteOrderAndItems() {
        Order savedOrder = orderRepository.save(testOrder);
        Long orderId = savedOrder.getId();

        orderRepository.deleteById(orderId);

        assertFalse(orderRepository.existsById(orderId));
    }

    @Test
    void updateOrderStatus_ShouldPersistStatusChange() {
        Order savedOrder = orderRepository.save(testOrder);
        savedOrder.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(savedOrder);

        Order updatedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.PROCESSING, updatedOrder.getStatus());
    }
} 