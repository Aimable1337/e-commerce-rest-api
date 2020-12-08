package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.exception.NotFoundException;
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
        if(productRepository.findAll().isEmpty())
            throw new NotFoundException("We have no products :O");
        return productRepository.findAll();
    }

    @Override
    public Product findByID(long theId) {
            return productRepository.findById(theId).orElseThrow(
                    () -> new NotFoundException("Product is not found by id: " + theId)
            );
    }

    @Override
    public void save(Product theProduct) {
        if(theProduct.getProductID() == 0 || productRepository.findById(theProduct.getProductID()).isPresent()){
            productRepository.save(theProduct);
        }else{
        throw new NotFoundException("Product with id: " + theProduct.getProductID() + " does not exist");
        }
    }

    @Override
    public void deleteByID(long theId) {
        if (productRepository.findById(theId).isPresent()) {
            productRepository.deleteById(theId);
        } else {
            throw new NotFoundException("Product with id: " + theId + " does not exist");
        }
    }
}

