package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserRestController(UserService userService, HeaderGenerator headerGenerator) {
        this.userService = userService;
        this.headerGenerator = headerGenerator;
    }

    public ResponseEntity<?> showMyDetails() throws Exception{
        throw new Exception("Not implemented yet!");
    }

    public ResponseEntity<List<Order>> showMyOrders() throws Exception{
        throw new Exception("Not implemented yet!");
    }

    public ResponseEntity<ResponseMessage> changeMyUserName() throws Exception{
        throw new Exception("Not implemented yet!");
    }

    public ResponseEntity<ResponseMessage> changeMyPassword() throws Exception{
        throw new Exception("Not implemented yet!");
    }

    public ResponseEntity<ResponseMessage> changeMyDetails() throws Exception{
        throw new Exception("Not implemented yet!");
    }
}