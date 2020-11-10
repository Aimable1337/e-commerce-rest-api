package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findByID(long theId);

    void save(Product theProduct);

    void deleteByID(long theId);
}
