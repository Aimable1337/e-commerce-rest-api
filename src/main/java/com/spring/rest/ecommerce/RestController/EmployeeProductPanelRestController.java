package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeProductPanelRestController {

    private final ProductService productService;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public EmployeeProductPanelRestController(ProductService theProductService,
                                              HeaderGenerator theHeaderGenerator,
                                              ResponseMessageGenerator responseMessageGenerator){
        this.headerGenerator = theHeaderGenerator;
        this.productService = theProductService;
        this.responseMessageGenerator = responseMessageGenerator;
    }

    @PostMapping("/products")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody Product theProduct,
                                                 HttpServletRequest request){
        theProduct.setProductID(0);
        productService.save(theProduct);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPostMethod(theProduct.getProductID()),
                headerGenerator.getHeadersForSuccessPostMethod(request, theProduct.getProductID()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/products")
    public ResponseEntity<ResponseMessage> updateProduct(@RequestBody Product theProduct,
                                                 HttpServletRequest request){
        productService.save(theProduct);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(theProduct.getProductID()),
                headerGenerator.getHeadersForSuccessPostMethod(request, theProduct.getProductID()),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/products/{theId}")
    public ResponseEntity<ResponseMessage> deleteProductById(@PathVariable long theId){
        productService.deleteByID(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessDeleteMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

}