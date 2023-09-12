package com.example.springproject.service;

import com.example.springproject.model.CategoryModel;
import com.example.springproject.model.ProductModel;
import com.example.springproject.repository.CategoryRepository;
import com.example.springproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    final private ProductRepository productRepository;
    final private CategoryRepository categoryRepository;

    @Autowired
    public StoreService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductModel> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductModel getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addProduct(ProductModel product) {
        productRepository.save(product);
    }


    public void updateProduct(ProductModel updatedProduct) {
        // Fetch the existing product from the database by its ID
        ProductModel existingProduct = productRepository.findById(updatedProduct.getId()).orElse(null);

        if (existingProduct != null) {
            // Update the fields of the existing product with the new values
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setImage(updatedProduct.getImage());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());

            // Save the updated product back to the database
            productRepository.save(existingProduct);
        }
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
