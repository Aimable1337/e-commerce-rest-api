package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.Product.NewProductCategoryDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductNameDTO;
import com.spring.rest.ecommerce.DTO.Product.NewProductPriceDTO;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.ProductEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeProductPanelRestController {

    private final ProductEditor productEditor;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public EmployeeProductPanelRestController(ProductEditor productEditor,
                                              HeaderGenerator HeaderGenerator,
                                              ResponseMessageGenerator responseMessageGenerator){
        this.headerGenerator = HeaderGenerator;
        this.productEditor = productEditor;
        this.responseMessageGenerator = responseMessageGenerator;
    }

    @PostMapping("/products")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody NewProductDTO newProductDTO,
                                                         HttpServletRequest request){
        long id = productEditor.createProduct(newProductDTO);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPostMethod(id),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), id),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/product/change-category/{id}")
    public ResponseEntity<ResponseMessage> changeProductCategory(@RequestBody NewProductCategoryDTO newProductCategoryDTO,
                                                                 @PathVariable long id,
                                                                 HttpServletRequest request){
        productEditor.changeProductCategory(newProductCategoryDTO, id);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(id),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), id),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/product/change-name/{id}")
    public ResponseEntity<ResponseMessage> changeProductName(@RequestBody NewProductNameDTO newProductNameDTO,
                                                             @PathVariable long id,
                                                             HttpServletRequest request){
        productEditor.changeProductName(newProductNameDTO, id);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(id),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), id),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/product/change-price/{id}")
    public ResponseEntity<ResponseMessage> changeProductPrice(@RequestBody NewProductPriceDTO newProductPriceDTO,
                                                              @PathVariable long id,
                                                              HttpServletRequest request){
        productEditor.changeProductPrice(newProductPriceDTO, id);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(id),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), id),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseMessage> deleteProductById(@PathVariable long id){
        productEditor.deleteById(id);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessDeleteMethod(id),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

}