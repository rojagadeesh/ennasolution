package com.ennea.solutions.products.repository;

import java.time.LocalDate;
import java.util.List;

import com.ennea.solutions.products.entity.ProductEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findBySupplierAndStockGreaterThan(String supplier, Integer stock, Pageable pageable);

    List<ProductEntity> findBySupplierAndNameAndStockGreaterThan(String supplier, String name, Integer stock, Pageable pageable);

    List<ProductEntity> findBySupplierAndExpGreaterThanAndStockGreaterThan(String supplier, LocalDate now, Integer i, Pageable pageable);

    List<ProductEntity> findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(String supplier, String name, LocalDate now, Integer i, Pageable pageable);
}
