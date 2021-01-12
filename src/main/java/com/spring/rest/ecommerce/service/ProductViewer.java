package com.spring.rest.ecommerce.service;


import com.spring.rest.ecommerce.DTO.Product.ProductViewDTO;

import java.util.List;

public interface ProductViewer {

    List<ProductViewDTO> viewAll();

    ProductViewDTO viewByID(long theId);

    ProductViewDTO viewByProductName(String productName);

    long getProductId(String productName);

}
