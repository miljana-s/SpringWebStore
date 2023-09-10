package com.example.springproject.model;

public class CartItemModel {
    private ProductModel product;
    private int quantity = 1; // Default quantity is 1

    public CartItemModel(ProductModel product) {
        this.product = product;
    }

    public ProductModel getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            this.quantity--;
        }
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
