package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeProductPanelRestController { //TODO: refactor class to fit new architecture

    private final ProductService productService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public EmployeeProductPanelRestController(ProductService theProductService,
                                 HeaderGenerator theHeaderGenerator){
        headerGenerator = theHeaderGenerator;
        productService = theProductService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product theProduct,
                                                 HttpServletRequest request){
        theProduct.setProductID(0);
        productService.save(theProduct);
        return new ResponseEntity<>(
                theProduct,
                headerGenerator.getHeadersForSuccessPostMethod(request, theProduct.getProductID()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product theProduct,
                                                 HttpServletRequest request){
        productService.save(theProduct);
        return new ResponseEntity<>(
                theProduct,
                headerGenerator.getHeadersForSuccessPostMethod(request, theProduct.getProductID()),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/products/{theId}")
    public ResponseEntity<String> deleteProductById(@PathVariable long theId){
        productService.deleteByID(theId);
        return new ResponseEntity<>(
                "Product deleted!",
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

}
