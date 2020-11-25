package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Order;
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
@RequestMapping("/employee/order-panel")
public class EmployeeOrderPanelRestController {

    private final OrderService orderService;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public EmployeeOrderPanelRestController(OrderService orderService,
                                            ResponseMessageGenerator responseMessageGenerator,
                                            HeaderGenerator headerGenerator) {
        this.orderService = orderService;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(
                orderService.findAll(),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/{theId}")
    public ResponseEntity<Order> getOrderBuId(@PathVariable long theId) {
        return new ResponseEntity<>(
                orderService.findOrderById(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order theOrder, HttpServletRequest request) {
        theOrder.setOrderId(0);
        orderService.save(theOrder);
        return new ResponseEntity<>(
                theOrder,
                headerGenerator.getHeadersForSuccessPostMethod(request, theOrder.getOrderId()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/orders")
    public ResponseEntity<ResponseMessage> updateOrder(@RequestBody Order theOrder) {
        orderService.save(theOrder);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(theOrder.getOrderId()),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/orders/{theId}")
    public ResponseEntity<?> changeOrdersStatus(/*OrderStatus status,*/ @PathVariable long theId) throws Exception{ //TODO: implement custom order's status
        throw new Exception("Not implemented yet!");
    }

    @DeleteMapping("/orders/{theId}")
    public ResponseEntity<ResponseMessage> deleteOrder(@PathVariable long theId) throws Exception {
        orderService.deleteById(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessDeleteMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}
