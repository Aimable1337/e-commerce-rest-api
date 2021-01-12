package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class NewProductCategoryDTO {

    private String newProductCategory;

    public Product changeCategory(Product source){
        return new Product(source.getProductID(), source.getProductName(), newProductCategory, source.getProductPrice());
    }

}