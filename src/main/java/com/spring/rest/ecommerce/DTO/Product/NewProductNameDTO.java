package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class NewProductNameDTO {

    private String newProductName;

    public Product changeName(Product source){
        return new Product(source.getProductID(), newProductName, source.getProductCategory(), source.getProductPrice());
    }

}