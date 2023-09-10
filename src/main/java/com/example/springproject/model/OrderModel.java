package com.example.springproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private double totalPrice;
    @Column(name = "is_active")
    private boolean active; // Represents the order status
    private LocalDateTime orderDate; // Date when the order was created

    public OrderModel() {
    }

    public OrderModel(String username, double totalPrice, boolean active, LocalDateTime orderDate) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.active = active;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", totalPrice=" + totalPrice +
                ", active=" + active +
                ", orderDate=" + orderDate +
                '}';
    }
}


