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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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


        cart.addItem(product);

        return "redirect:/products";
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
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null && !cart.getItems().isEmpty()) {

            OrderModel order = cart.convertToOrder(principal.getName());
            orderRepository.save(order);


            cart.clear();

        }
        return "redirect:/cart?success=true";
    }

    @PostMapping("/declineCart")
    public String declineCart(HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null) {

            cart.clear();
        }
        return "redirect:/products";
    }

    @RequestMapping("/removeItem/{id}")
    public String removeCartItem(@PathVariable("id") Long id, Model model, HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null) {
            cart.getItems().removeIf(cartItemModel -> cartItemModel.getProduct().getId() == id);
        }
        else {
            cart = new CartModel();
        }
        model.addAttribute("cart", cart);
        return "cart";
}

    @RequestMapping("/increaseItem/{id}")
    public String increaseCartItem(@PathVariable("id") Long id, Model model, HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null) {
            for (CartItemModel cartItem: cart.getItems()){
                    if (cartItem.getProduct().getId() == id){
                            int q = cartItem.getQuantity();
                            cartItem.setQuantity(q + 1);
                            break;
                    }
            }
        }
        else {
            cart = new CartModel();
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping("/decreseItem/{id}")
    public String decreseCartItem(@PathVariable("id") Long id, Model model, HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart != null) {
            for (CartItemModel cartItem: cart.getItems()){
                if (cartItem.getProduct().getId() == id){
                    int q = cartItem.getQuantity();
                    cartItem.setQuantity(q - 1);
                    if (cartItem.getQuantity() == 0){
                            cart.getItems().remove(cartItem);
                        }
                    break;
                }
            }
        }
        else {
            cart = new CartModel();
        }
        model.addAttribute("cart", cart);
        return "cart";
    }
}
