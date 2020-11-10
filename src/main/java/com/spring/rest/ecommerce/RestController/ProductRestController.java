package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService theProductService){
        productService = theProductService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductS(){
        return ResponseEntity.ok().body(productService.findAll());
    }



}
