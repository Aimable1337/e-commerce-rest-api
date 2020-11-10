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
        if (productRepository.findById(theId).isPresent()){
            return productRepository.findById(theId).get();
        } else {
            throw new RuntimeException("Did not found product by id - " + theId);
        }
    }

    @Override
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteByID(long theId) {
        if (productRepository.findById(theId).isPresent()){
            productRepository.deleteById(theId);
        } else {
            throw new RuntimeException("Did not found product by id - " + theId);
        }
    }
}
