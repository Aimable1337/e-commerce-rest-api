package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class NewProductPriceDTO {

    private int newProductPrice;

    public Product changePrice(Product source){
        return new Product(source.getProductID(), source.getProductName(), source.getProductCategory(), newProductPrice);
    }

}