package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/orders")
public class UserOrdersRestController {

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @GetMapping("/{theId}")
    public ResponseEntity<Order> getMyOrderById(@PathVariable long theId) throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @GetMapping("/orders-status/{theId}")
    public ResponseEntity<?> getMyOrdersStatus() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody List<Product> products) throws Exception {
        throw new Exception("Not implemented yet!");
    }

}