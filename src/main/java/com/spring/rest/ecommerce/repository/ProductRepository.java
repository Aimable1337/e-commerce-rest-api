package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByProductName(String productName);

    @Query("SELECT p.productID from Product p where p.productName = ?1")
    Optional<Long> findProductId(String productName);
}
