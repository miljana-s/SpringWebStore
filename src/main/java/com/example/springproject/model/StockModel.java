package com.example.springproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private ProductSizesModel size;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int quantity;

    public StockModel() {
    }

    public StockModel(ProductModel product, ProductSizesModel size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public ProductSizesModel getSize() {
        return size;
    }

    public void setSize(ProductSizesModel size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockModel{" +
                "id=" + id +
                ", product=" + product +
                ", size=" + size +
                ", quantity=" + quantity +
                '}';
    }
}
