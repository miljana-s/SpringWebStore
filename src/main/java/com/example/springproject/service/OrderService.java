package com.example.springproject.service;

import com.example.springproject.model.OrderModel;
import com.example.springproject.model.OrderStatusEnum;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void confirmOrder(Long orderId,  Set<ProductModel> products) {
        OrderModel order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.getProducts().addAll(products);
            order.setStatus(OrderStatusEnum.CONFIRMED); // Set order status to active
            orderRepository.save(order);
        }
    }

    public void declineOrder(Long orderId) {
        OrderModel order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderRepository.delete(order); // Delete the declined order
        }
    }

    public List<OrderModel> getAllOrders() {
        // Use the orderRepository to fetch all orders
        return orderRepository.findAll();
    }
}
