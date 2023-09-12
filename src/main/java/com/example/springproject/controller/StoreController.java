package com.example.springproject.controller;

import com.example.springproject.model.CartModel;
import com.example.springproject.model.CategoryModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.CategoryRepository;
import com.example.springproject.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if (cart == null) {
            cart = new CartModel();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart );
        return "products";
    }


    @GetMapping("/products/{id}")
    public String viewProductDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        ProductModel product = storeService.getProductById(id);
        if (product != null) {
            CartModel cart = (CartModel) session.getAttribute("cart");
            if (cart == null) {
                    cart = new CartModel();
                    session.setAttribute("cart", cart);
                }
            model.addAttribute("cart", cart);
            model.addAttribute("selectedProduct", product);
            return "product-overview";
        } else {

            return "error";
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


    @GetMapping("/update-product/{id}")
    public String showUpdateProductForm(
            @PathVariable("id") Long id,
            @RequestParam(name = "success", required = false) Boolean success,
            Model model
    ) {
        ProductModel product = storeService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            List<CategoryModel> categories = storeService.getAllCategories();
            model.addAttribute("categories", categories);


            model.addAttribute("showUpdateMessage", success != null && success);

            return "update-product";
        } else {
            return "error";
        }
    }

    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute ProductModel updatedProduct, Model model) {

        storeService.updateProduct(updatedProduct);

        model.addAttribute("showUpdateMessage", true);

        return "redirect:/update-product/" + updatedProduct.getId() + "?success=true";
    }


    @PostMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        storeService.deleteProductById(id);


        model.addAttribute("showDeleteMessage", true);

        // Redirect back to the products page with a success message
        return "redirect:/products?success=true";
    }

}
