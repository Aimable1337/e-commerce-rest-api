package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.Product.ProductViewDTO;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductViewerImpl implements ProductViewer{

    private final ProductRepository productRepository;

    @Autowired
    public  ProductViewerImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductViewDTO> viewAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty())
            throw new NotFoundException("We have no products");

        return products.stream()
                .map(ProductViewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewDTO viewByID(long theId) {
        return productRepository.findById(theId)
                .map(ProductViewDTO::new)
                .orElseThrow(() -> new NotFoundException("Product with id: " + theId + " not found"));
    }

    @Override
    public ProductViewDTO viewByProductName(String productName) {
        return productRepository.findProductByProductName(productName)
                .map(ProductViewDTO::new)
                .orElseThrow(() -> new NotFoundException("Product with name: " + productName + " not found"));
    }

    @Override
    public long getProductId(String productName) {
        return productRepository.findProductId(productName)
                .orElseThrow(() -> new NotFoundException("Product with name: " + productName + " not found"));
    }
}
