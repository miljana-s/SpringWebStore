package com.example.springproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status; // Represents the order status
    private LocalDateTime orderDate; // Date when the order was created

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductModel> products = new HashSet<>();


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

    public Set<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductModel> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", products=" + products +
                '}';
    }
}


