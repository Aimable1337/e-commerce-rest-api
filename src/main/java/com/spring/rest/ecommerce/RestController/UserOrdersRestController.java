package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserOrdersRestController {

    private final OrderService orderService;

    private final ResponseMessageGenerator responseMessageGenerator;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserOrdersRestController(OrderService orderService,
                                    HeaderGenerator headerGenerator,
                                    ResponseMessageGenerator responseMessageGenerator) {
        this.orderService = orderService;
        this.headerGenerator = headerGenerator;
        this.responseMessageGenerator = responseMessageGenerator;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        return new ResponseEntity<>(
                orderService.findMyOrders(request.getRemoteUser()),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/{theId}")
    public ResponseEntity<Order> getMyOrderById(@PathVariable long theId) {
        return new ResponseEntity<>(
                orderService.findOrderById(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/order-status/{theId}") // TODO
    public ResponseEntity<?> getMyOrdersStatus() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseMessage> createOrder(@RequestBody List<Product> products, HttpServletRequest request) {
        long newOrderId = orderService.createOrder(products, request);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(newOrderId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

}