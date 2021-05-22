package com.spring.catalog.products.repository;

import com.spring.catalog.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT product FROM Product product WHERE product.name LIKE %:term% OR product.description LIKE %:term%")
    List<Product> findAllByTerm(String term);
}
