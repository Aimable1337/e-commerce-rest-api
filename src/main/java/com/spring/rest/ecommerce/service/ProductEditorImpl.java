package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.Product.NewProductCategoryDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductNameDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductPriceDTO;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.exception.ProductAlreadyExistException;
import com.spring.rest.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEditorImpl implements ProductEditor{

    private final ProductRepository productRepository;

    @Autowired
    public ProductEditorImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public long createProduct(NewProductDTO newProductDTO) {
        Product newProduct = newProductDTO.toEntity();
        if (productRepository.findProductByProductName(newProduct.getProductName()).isPresent())
            throw new ProductAlreadyExistException("Product with this name already exist");
        productRepository.save(newProduct);
        return newProduct.getProductID();
    }

    @Override
    public void changeProductName(NewProductNameDTO newProductNameDTO, long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product with id: " + id + " not found")
        );
        productRepository.save(newProductNameDTO.changeName(product));
    }

    @Override
    public void changeProductCategory(NewProductCategoryDTO newProductCategoryDTO, long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product with id: " + id + " not found")
        );
        productRepository.save(newProductCategoryDTO.changeCategory(product));
    }

    @Override
    public void changeProductPrice(NewProductPriceDTO newProductPriceDTO, long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product with id: " + id + " not found")
        );
        productRepository.save(newProductPriceDTO.changePrice(product));
    }

    @Override
    public void deleteById(long id) {
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException("Product with id: " + id + " not found");
        }
    }
}
