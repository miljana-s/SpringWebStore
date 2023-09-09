package com.example.springproject.controller;

import com.example.springproject.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    @GetMapping("/products")
    public String getProductsPage(Model model){
        return "products";
    }
}
