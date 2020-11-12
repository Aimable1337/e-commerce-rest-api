package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository theProductRepository){
        productRepository = theProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findByID(long theId) {
        return productRepository.findById(theId).get();
    }

    @Override
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteByID(long theId) {
        productRepository.deleteById(theId);
    }
}

