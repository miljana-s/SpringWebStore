package com.example.springproject.repository;

import com.example.springproject.model.CategoryEnum;
import com.example.springproject.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    CategoryModel findByName(CategoryEnum name);
}
