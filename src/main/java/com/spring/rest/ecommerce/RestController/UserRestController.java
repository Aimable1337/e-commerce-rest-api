package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user-api")
public class UserRestController {

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserRestController(UserService userService, HeaderGenerator headerGenerator) {
        this.userService = userService;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(
                userService.findAll(),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/{theId}")
    public ResponseEntity<User> getUserById(@PathVariable long theId){
        return new ResponseEntity<>(
                userService.findByID(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(
            @RequestBody User theUser,
            HttpServletRequest request
            ){
        theUser.setUserId(0);
        userService.save(theUser);
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserId()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User theUser, HttpServletRequest request){
        userService.save(theUser);
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserId()),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<String> deleteUserById(@PathVariable long theId){
        userService.deleteByID(theId);
        return new ResponseEntity<>(
                "User with id " + theId + " deleted.",
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}
