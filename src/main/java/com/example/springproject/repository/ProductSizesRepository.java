package com.example.springproject.repository;

import com.example.springproject.model.ProductSizesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizesRepository extends JpaRepository<ProductSizesModel, Long> {
}
