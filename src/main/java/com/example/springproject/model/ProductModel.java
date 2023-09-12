package com.example.springproject.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private float price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @OneToMany(mappedBy = "product")
    Set<OrderItemModel> orderItems = new HashSet<>();

    public ProductModel() {
    }

    public ProductModel(String name, String image, float price, CategoryModel category, Set<OrderItemModel> orderItems) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public Set<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Float.compare(that.price, price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(image, that.image) && Objects.equals(category, that.category) && Objects.equals(orderItems, that.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, price, category, orderItems);
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", orderItems=" + orderItems +
                '}';
    }
}
