package com.example.springproject.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_sizes")
public class ProductSizesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SizeEnum name;

    @ManyToMany(mappedBy = "sizes")
    private Set<ProductModel> products = new HashSet<>();

    public ProductSizesModel() {
    }

    public ProductSizesModel(SizeEnum name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SizeEnum getName() {
        return name;
    }

    public void setName(SizeEnum name) {
        this.name = name;
    }

    public Set<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductModel> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductSizesModel{" +
                "id=" + id +
                ", name=" + name +
                ", products=" + products +
                '}';
    }
}
