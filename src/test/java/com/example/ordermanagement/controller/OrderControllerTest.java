package com.example.ordermanagement.controller;

import com.example.ordermanagement.exception.OrderNotFoundException;
import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.OrderItem;
import com.example.ordermanagement.model.OrderStatus;
import com.example.ordermanagement.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order testOrder;
    private OrderItem testOrderItem;
    private Order cancelledOrder;
    private Order processingOrder;

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

        processingOrder = new Order();
        processingOrder.setId(1L);
        processingOrder.setCustomerName("Test Customer");
        processingOrder.setCustomerEmail("test@example.com");
        processingOrder.setStatus(OrderStatus.PROCESSING);
        processingOrder.addItem(testOrderItem);

        cancelledOrder = new Order();
        cancelledOrder.setId(1L);
        cancelledOrder.setCustomerName("Test Customer");
        cancelledOrder.setCustomerEmail("test@example.com");
        cancelledOrder.setStatus(OrderStatus.CANCELLED);
        cancelledOrder.addItem(testOrderItem);
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.customerName").value(testOrder.getCustomerName()))
                .andExpect(jsonPath("$.status").value(testOrder.getStatus().toString()));
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(testOrder);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.customerName").value(testOrder.getCustomerName()))
                .andExpect(jsonPath("$.status").value(testOrder.getStatus().toString()));
    }

    @Test
    void getOrderById_WhenOrderDoesNotExist_ShouldReturn404() throws Exception {
        when(orderService.getOrderById(1L)).thenThrow(new OrderNotFoundException("Order not found"));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOrders_ShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testOrder.getId()))
                .andExpect(jsonPath("$[0].customerName").value(testOrder.getCustomerName()))
                .andExpect(jsonPath("$[0].status").value(testOrder.getStatus().toString()));
    }

    @Test
    void getOrdersByStatus_ShouldReturnFilteredOrders() throws Exception {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getOrdersByStatus(OrderStatus.PENDING)).thenReturn(orders);

        mockMvc.perform(get("/api/orders?status=PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testOrder.getId()))
                .andExpect(jsonPath("$[0].status").value(OrderStatus.PENDING.toString()));
    }

    @Test
    void updateOrderStatus_WhenOrderExists_ShouldUpdateStatus() throws Exception {
        when(orderService.updateOrderStatus(1L, OrderStatus.PROCESSING)).thenReturn(processingOrder);

        mockMvc.perform(put("/api/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"PROCESSING\"")
                .queryParam("status", OrderStatus.PROCESSING.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.status").value(OrderStatus.PROCESSING.toString()));
    }

    @Test
    void cancelOrder_WhenOrderIsPending_ShouldCancelOrder() throws Exception {
        when(orderService.cancelOrder(1L)).thenReturn(cancelledOrder);

        mockMvc.perform(put("/api/orders/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.status").value(OrderStatus.CANCELLED.toString()));
    }

    @Test
    void cancelOrder_WhenOrderIsNotPending_ShouldReturn400() throws Exception {
        when(orderService.cancelOrder(1L)).thenThrow(new IllegalStateException("Only PENDING orders can be cancelled"));

        mockMvc.perform(put("/api/orders/1/cancel"))
                .andExpect(status().isBadRequest());
    }
} 