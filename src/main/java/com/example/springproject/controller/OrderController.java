package com.example.springproject.controller;

import com.example.springproject.model.OrderModel;
import com.example.springproject.model.OrderStatusEnum;
import com.example.springproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<OrderModel> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/confirmOrder/{orderId}")
    public String confirmOrder(@PathVariable("orderId") Long orderId) {
        OrderModel order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatusEnum.CONFIRMED); // Set order status to active
            orderRepository.save(order);
        }
        return "redirect:/orders";
    }

    @PostMapping("/declineOrder/{orderId}")
    public String declineOrder(@PathVariable("orderId") Long orderId) {
        OrderModel order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderRepository.delete(order); // Delete the declined order
        }
        return "redirect:/orders";
    }
}
