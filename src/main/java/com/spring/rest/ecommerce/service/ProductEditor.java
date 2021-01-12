package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.Product.NewProductCategoryDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductNameDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductPriceDTO;

public interface ProductEditor {

    long createProduct(NewProductDTO newProductDTO);

    void changeProductName(NewProductNameDTO newProductNameDTO, long od);

    void changeProductCategory(NewProductCategoryDTO newProductCategoryDTO, long id);

    void changeProductPrice(NewProductPriceDTO newProductPriceDTO, long id);

    void deleteById(long id);

}
