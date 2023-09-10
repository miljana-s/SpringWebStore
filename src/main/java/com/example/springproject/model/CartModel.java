package com.example.springproject.model;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private List<CartItemModel> items = new ArrayList<>();

    public void addItem(ProductModel product) {
        CartItemModel existingItem = findItem(product.getId());
        if (existingItem != null) {
            existingItem.incrementQuantity();
        } else {
            items.add(new CartItemModel(product));
        }
    }

    public List<CartItemModel> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItemModel::getSubtotal).sum();
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void updateQuantity(Long productId, int newQuantity) {
        CartItemModel item = findItem(productId);
        if (item != null) {
            item.setQuantity(newQuantity);
        }
    }

    public void clear() {
        items.clear();
    }

    private CartItemModel findItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}

