package com.example.springproject.controller;

import com.example.springproject.model.CartItemModel;
import com.example.springproject.model.CartModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class StoreController {

    final private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/products")
    public String getProductsPage(Model model, HttpSession session){
        model.addAttribute("product_list", this.storeService.getAllProducts());
        CartModel cart = (CartModel) session.getAttribute("cart");
        int itemsInCart = 0;
        if (cart != null) {
            for ( CartItemModel item : cart.getItems()){
                itemsInCart += item.getQuantity();
            }
        }
        model.addAttribute("itemsInCart", itemsInCart );
        return "products";
    }


    @GetMapping("/products/{id}")
    public String viewProductDetails(@PathVariable("id") Long id, Model model) {
        ProductModel product = storeService.getProductById(id);
        if (product != null) {
            model.addAttribute("selectedProduct", product);
            return "product-overview"; // This corresponds to the product.html template
        } else {
            // Handle product not found (e.g., show an error message)
            return "error"; // Create an error.html template for error handling
        }
    }


}
