package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.UserDetails;
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
    public ResponseEntity<List<UserDetails>> getUsers(){
        List<UserDetails> users = userService.findAll();
            return new ResponseEntity<>(
                    users,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @GetMapping("/users/{theId}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable long theId){
            return new ResponseEntity<>(
                    userService.findByID(theId),
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @PostMapping("/users")
    public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails theUser,
                                                  HttpServletRequest request){
        theUser.setUserID(0);
        userService.save(theUser);
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserID()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/users")
    public ResponseEntity<UserDetails> updateUser(@RequestBody UserDetails theUser,
                                                  HttpServletRequest request){
            userService.save(theUser);
            return new ResponseEntity<>(
                    theUser,
                    headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserID()),
                    HttpStatus.ACCEPTED
            );
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<String> deleteUsersById(@PathVariable long theId){
            userService.deleteByID(theId);
            return new ResponseEntity<>(
                    "User deleted!",
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.ACCEPTED
            );
    }

}
