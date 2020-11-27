package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
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

    @Autowired
    public RegisterRestController(UserService userService, HeaderGenerator headerGenerator){
        this.userService = userService;
        this.headerGenerator = headerGenerator;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User theUser, HttpServletRequest request) {
        theUser.setUserId(0);
        userService.save(theUser);
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserId()),
                HttpStatus.CREATED
        );
    }
}