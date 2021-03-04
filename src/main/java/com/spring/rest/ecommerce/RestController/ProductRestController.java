package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.Product.ProductViewDTO;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.ProductViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductViewer productViewer;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public ProductRestController(ProductViewer productViewer,
                                 HeaderGenerator headerGenerator){
        this.headerGenerator = headerGenerator;
        this.productViewer = productViewer;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDTO>> getProducts(){
        List<ProductViewDTO> products = productViewer.viewAll();
            return new ResponseEntity<>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @GetMapping("/products/{theId}")
    public ResponseEntity<ProductViewDTO> getProductById(@PathVariable long theId){
            return new ResponseEntity<>(
                    productViewer.viewByID(theId),
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

}