package com.example.ordermanagement.service;

import com.example.ordermanagement.exception.OrderNotFoundException;
import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.OrderItem;
import com.example.ordermanagement.model.OrderStatus;
import com.example.ordermanagement.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setProductName("Test Product");
        testOrderItem.setQuantity(2);
        testOrderItem.setPrice(10.0);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setCustomerName("Test Customer");
        testOrder.setCustomerEmail("test@example.com");
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.addItem(testOrderItem);
    }

    @Test
    void createOrder_ShouldReturnSavedOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order savedOrder = orderService.createOrder(testOrder);

        assertNotNull(savedOrder);
        assertEquals(testOrder.getId(), savedOrder.getId());
        assertEquals(testOrder.getCustomerName(), savedOrder.getCustomerName());
        assertEquals(testOrder.getStatus(), savedOrder.getStatus());
        verify(orderRepository).save(testOrder);
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(testOrder.getId(), foundOrder.getId());
        assertEquals(testOrder.getCustomerName(), foundOrder.getCustomerName());
    }

    @Test
    void getOrderById_WhenOrderDoesNotExist_ShouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> foundOrders = orderService.getAllOrders();

        assertNotNull(foundOrders);
        assertEquals(1, foundOrders.size());
        assertEquals(testOrder.getId(), foundOrders.get(0).getId());
    }

    @Test
    void getOrdersByStatus_ShouldReturnFilteredOrders() {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(orders);

        List<Order> foundOrders = orderService.getOrdersByStatus(OrderStatus.PENDING);

        assertNotNull(foundOrders);
        assertEquals(1, foundOrders.size());
        assertEquals(OrderStatus.PENDING, foundOrders.get(0).getStatus());
    }

    @Test
    void updateOrderStatus_WhenOrderExists_ShouldUpdateStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order updatedOrder = orderService.updateOrderStatus(1L, OrderStatus.PROCESSING);

        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.PROCESSING, updatedOrder.getStatus());
        verify(orderRepository).save(testOrder);
    }

    @Test
    void updateOrderStatus_WhenOrderDoesNotExist_ShouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus(1L, OrderStatus.PROCESSING));
    }

    @Test
    void cancelOrder_WhenOrderIsPending_ShouldCancelOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order cancelledOrder = orderService.cancelOrder(1L);

        assertNotNull(cancelledOrder);
        assertEquals(OrderStatus.CANCELLED, cancelledOrder.getStatus());
        verify(orderRepository).save(testOrder);
    }

    @Test
    void cancelOrder_WhenOrderIsNotPending_ShouldThrowException() {
        testOrder.setStatus(OrderStatus.PROCESSING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(1L));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updatePendingOrdersToProcessing_ShouldUpdateAllPendingOrders() {
        List<Order> pendingOrders = Arrays.asList(testOrder);
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(pendingOrders);
        when(orderRepository.saveAll(anyList())).thenReturn(pendingOrders);

        orderService.updatePendingOrdersToProcessing();

        verify(orderRepository).findByStatus(OrderStatus.PENDING);
        verify(orderRepository).saveAll(pendingOrders);
        assertEquals(OrderStatus.PROCESSING, pendingOrders.get(0).getStatus());
    }
} 