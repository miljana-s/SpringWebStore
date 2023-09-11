package com.example.springproject.controller;

import com.example.springproject.model.CartItemModel;
import com.example.springproject.model.CartModel;
import com.example.springproject.model.CategoryModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.CategoryRepository;
import com.example.springproject.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class StoreController {

    final private StoreService storeService;
    final private CategoryRepository categoryRepository;

    @Autowired
    public StoreController(StoreService storeService, CategoryRepository categoryRepository) {
        this.storeService = storeService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String getHomePage(){
        return "index";
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

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        // Populate the model with a list of categories for the dropdown
        List<CategoryModel> categories = storeService.getAllCategories();
        model.addAttribute("categories", categories);
        return "add-product";
    }


    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductModel newProduct, Model model) {
        // Fetch the selected category using its ID from the form
        CategoryModel selectedCategory = categoryRepository.findById(newProduct.getCategory().getId()).orElse(null);
        if (selectedCategory != null) {
            // Set the selected category on the new product
            newProduct.setCategory(selectedCategory);
            // Save the new product to the database
            storeService.addProduct(newProduct);

            model.addAttribute("showSuccessMessage", true);

            return "add-product";

        } else {

            return "error";
        }
    }



}
