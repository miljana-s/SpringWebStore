package com.example.springproject.controller;

import com.example.springproject.model.OrderModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.OrderRepository;
import com.example.springproject.service.OrderService;
import com.example.springproject.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final StoreService storeService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService, StoreService storeService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.storeService = storeService;
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<OrderModel> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        return "orders";
    }

    @PostMapping("/confirmOrder/{orderId}")
    public String confirmOrder(@PathVariable Long orderId, @RequestParam List<Long> productIds ) {

        Set<ProductModel> products = productIds.stream()
                .map(productId -> storeService.getProductById(productId))
                .collect(Collectors.toSet());

        orderService.confirmOrder(orderId, products);

        return "redirect:/orders";
    }

    @PostMapping("/declineOrder/{orderId}")
    public String declineOrder(@PathVariable("orderId") Long orderId) {

        orderService.declineOrder(orderId);

        return "redirect:/orders";
    }
}
