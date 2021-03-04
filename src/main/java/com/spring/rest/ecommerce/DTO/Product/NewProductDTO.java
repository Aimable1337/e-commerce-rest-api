package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class NewProductDTO {

    private String productName;

    private String productCategory;

    private int productPrice;

    public Product toEntity(){
        return new Product(0, productName, productCategory, productPrice);
    }
}