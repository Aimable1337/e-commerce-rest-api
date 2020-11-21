package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController { //TODO: set up all needed methods

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserRestController(UserService userService, HeaderGenerator headerGenerator) {
        this.userService = userService;
        this.headerGenerator = headerGenerator;
    }

}