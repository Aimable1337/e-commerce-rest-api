package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
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
        if (!products.isEmpty()){
            return new ResponseEntity<>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/products/{theId}")
    public ResponseEntity<Product> getProductById(@PathVariable long theId){
        Product product = productService.findByID(theId);
        if (product != null){

            return new ResponseEntity<>(
                    product,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
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

    @PostMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product theProduct,
                                                 HttpServletRequest request){
        if(productService.findByID(theProduct.getProductID()) != null){
            productService.save(theProduct);
            return new ResponseEntity<>(
                    theProduct,
                    headerGenerator.getHeadersForSuccessPostMethod(request, theProduct.getProductID()),
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                theProduct,
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/products/{theId}")
    public ResponseEntity<String> deleteProductById(@PathVariable long theId){
        if(productService.findByID(theId) != null){
            productService.deleteByID(theId);
            return new ResponseEntity<>(
                    "Product deleted!",
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                "Product doesn't exist!",
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

}
