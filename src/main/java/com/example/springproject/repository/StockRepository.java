package com.example.springproject.repository;

import com.example.springproject.model.StockModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockModel, Long> {
}
