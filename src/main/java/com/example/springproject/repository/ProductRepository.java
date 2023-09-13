package com.example.springproject.repository;

import com.example.springproject.model.CategoryModel;
import com.example.springproject.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %?1%")
    List<ProductModel> search(@Param("keyword") String keyword);

    @Query("SELECT p FROM ProductModel p WHERE p.category = ?1")
    List<ProductModel> findAllByCategory(CategoryModel category);
}
