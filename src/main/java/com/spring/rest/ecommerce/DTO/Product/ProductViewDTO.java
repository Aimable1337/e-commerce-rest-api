package com.spring.rest.ecommerce.DTO.Product;

import com.spring.rest.ecommerce.entity.Product;

public class ProductViewDTO {

    private long productId;

    private String productName;

    private int productPrice;

    private String productCategory;

    public ProductViewDTO(Product source){
        this.productId = source.getProductID();
        this.productName = source.getProductName();
        this.productPrice = source.getProductPrice();
        this.productCategory = source.getProductCategory();
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductCategory() {
        return productCategory;
    }
}
