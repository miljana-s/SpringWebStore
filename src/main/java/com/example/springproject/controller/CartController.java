package com.example.springproject.controller;

import com.example.springproject.model.CartItemModel;
import com.example.springproject.model.CartModel;
import com.example.springproject.model.OrderModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.OrderRepository;
import com.example.springproject.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final StoreService storeService;
    private final OrderRepository orderRepository;

    @Autowired
    public CartController(StoreService storeService, OrderRepository orderRepository) {
        this.storeService = storeService;
        this.orderRepository = orderRepository;
    }

    // Handle adding items to the cart
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId, HttpSession session) {
        // Get the product by ID from your service
        ProductModel product = storeService.getProductById(productId);

        // Retrieve the user's cart from the session or database
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartModel();
            session.setAttribute("cart", cart);
        }

        // Add the product to the cart
        cart.addItem(product);

        return "redirect:/products"; // Redirect back to the product page
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartModel();
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/confirmCart")
    public String confirmCart(HttpSession session, Principal principal) {
        System.out.println("Confirm Cart method called!");
        // Retrieve the cart from the session
        CartModel cart = (CartModel) session.getAttribute("cart");

        // Check if the cart and principal are not null
        if (cart != null && principal != null && !cart.getItems().isEmpty()) {
            try {
                // Convert cart to an order and save it
                OrderModel order = cart.convertToOrder(principal.getName());

                // Associate all products in the cart with the order
                order.setProducts(cart.getItems().stream()
                        .map(CartItemModel::getProduct)
                        .collect(Collectors.toSet()));

                orderRepository.save(order);

                // Clear the cart
                cart.clear();

                // Redirect to the orders page after successful confirmation
                return "redirect:/orders"; // You should have a mapping for "/orders" in your controller
            } catch (Exception e) {
                // Handle any exceptions (e.g., database errors) gracefully
                e.printStackTrace(); // Log the exception for debugging
                // You might want to add an error message to the model or redirect to an error page
            }
        }

        // If the cart is empty or the confirmation failed, you can redirect to another page or show an error message.
        return "redirect:/cart";
    }

    @PostMapping("/declineCart")
    public String declineCart(HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null) {
            // Clear the cart
            cart.clear();
        }
        return "redirect:/products"; // Redirect to the products page or another appropriate page
    }


}
