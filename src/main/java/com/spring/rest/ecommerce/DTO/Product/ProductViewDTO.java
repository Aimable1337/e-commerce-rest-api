package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class ProductViewDTO {

    private String productName;

    private int productPrice;

    public ProductViewDTO(Product source){
        this.productName = source.getProductName();
        this.productPrice = source.getProductPrice();
    }
}
