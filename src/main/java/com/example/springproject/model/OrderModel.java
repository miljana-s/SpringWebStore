package com.example.springproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
    private LocalDateTime orderDate;

    public OrderModel() {
    }

    public OrderModel(String username, double totalPrice, OrderStatusEnum status, LocalDateTime orderDate) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isConfirmed(){
        return this.status == OrderStatusEnum.CONFIRMED;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderDate=" + orderDate +
                '}';
    }
}


