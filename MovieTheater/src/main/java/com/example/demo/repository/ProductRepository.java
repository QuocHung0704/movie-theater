package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    List<Product> findAllActive();

    @Query("SELECT p FROM Product p WHERE p.productId = :id AND p.isActive = true")
    Optional<Product> findByIdIsActive(Long id);

    @Query("SELECT p FROM Product p WHERE p.productType = :type AND p.isActive = true")
    List<Product> getProductByType(@Param("type")ProductType type);
}
