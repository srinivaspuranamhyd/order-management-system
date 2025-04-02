package com.example.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Integer quantity;
    private Double  price;

    @ManyToOne
    @JoinColumn(nullable=false, name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    public OrderItem(String productName, Integer quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Double getSubtotal() {
        return quantity != null && price != null ? quantity * price : 0.0;
    }
} 