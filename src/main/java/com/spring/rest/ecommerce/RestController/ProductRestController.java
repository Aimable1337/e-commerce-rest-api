package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public ProductRestController(ProductService theProductService,
                                 HeaderGenerator theHeaderGenerator){
        headerGenerator = theHeaderGenerator;
        productService = theProductService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.findAll();
            return new ResponseEntity<>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @GetMapping("/products/{theId}")
    public ResponseEntity<Product> getProductById(@PathVariable long theId){
            return new ResponseEntity<>(
                    productService.findByID(theId),
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

}