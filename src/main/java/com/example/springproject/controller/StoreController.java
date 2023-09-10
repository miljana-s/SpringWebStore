package com.example.springproject.controller;

import com.example.springproject.model.ProductModel;
import com.example.springproject.service.StoreService;
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
    public String getProductsPage(Model model){
        model.addAttribute("product_list", this.storeService.getAllProducts());
        return "products";
    }


    @GetMapping("/products/{id}")
    public String viewProductDetails(@PathVariable("id") Long id, Model model) {
        ProductModel product = storeService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-overview"; // This corresponds to the product.html template
        } else {
            // Handle product not found (e.g., show an error message)
            return "error"; // Create an error.html template for error handling
        }
    }


}
