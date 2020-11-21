package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/order-panel")
public class EmployeeOrderPanelRestController { //TODO: implement methods

    private OrderService orderService;

    private HeaderGenerator headerGenerator;

    @Autowired
    public EmployeeOrderPanelRestController(OrderService orderService,
                                            HeaderGenerator headerGenerator) {
        this.orderService = orderService;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() throws Exception{
        throw new Exception("Not implemented yet!");
    }

    @GetMapping("/orders/{theId}")
    public ResponseEntity<Order> getOrderBuId(@PathVariable long theId) throws Exception{
        throw new Exception("Not implemented yet!");
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order theOrder) throws Exception{
        throw new Exception("Not implemented yet!");
    }

    @PutMapping("/orders")
    public ResponseEntity<?> updateOrder(@RequestBody Order theOrder) throws Exception{
        throw new Exception("Not implemented yet!");
    }

    @PutMapping("/orders/{theId}")
    public ResponseEntity<?> changeOrdersStatus(/*OrderStatus status,*/ @PathVariable long theId) throws Exception{ //TODO: implement custom order's status
        throw new Exception("Not implemented yet!");
    }

    @DeleteMapping("/orders/{theId}")
    public ResponseEntity<?> deleteOrder(@PathVariable long theId) throws Exception {
        throw new Exception("Not implemented yet!");
    }
}
