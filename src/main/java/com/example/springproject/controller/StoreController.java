package com.example.springproject.controller;

import com.example.springproject.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
