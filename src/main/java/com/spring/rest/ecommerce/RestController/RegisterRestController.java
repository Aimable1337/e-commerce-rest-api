package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
public class RegisterRestController {

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public RegisterRestController(UserService userService,
                                  ResponseMessageGenerator responseMessageGenerator,
                                  HeaderGenerator headerGenerator){
        this.userService = userService;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody User newUser, HttpServletRequest request) {
        userService.register(newUser);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPostMethod(newUser.getUserId()),
                headerGenerator.getHeadersForSuccessPostMethod(request, newUser.getUserId()),
                HttpStatus.CREATED
        );
    }
}