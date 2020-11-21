package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.UserDetailsService;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user-panel")
public class AdminUserPanelRestController { //TODO: implement methods

    private UserService userService;

    private UserDetailsService userDetailsService;

    private HeaderGenerator headerGenerator;

    @Autowired
    public AdminUserPanelRestController(UserService userService,
                                        UserDetailsService userDetailsService,
                                        HeaderGenerator headerGenerator) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @GetMapping("/users/{theId}")
    public ResponseEntity<User> getSingleUser(@PathVariable long theId) throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @PutMapping("/users/{theId}")
    public ResponseEntity<String> changeAuthorities(@PathVariable long theId,
                                                    @RequestBody UserAuthority newAuthority) throws Exception{
        throw new Exception("Not implemented yet!");
    }

    @PutMapping("/ban-user/{theId}")
    public ResponseEntity<User> banUser(@PathVariable long theId) throws Exception {
        throw new Exception("Not implemented yet!");
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<String> deleteUser(@PathVariable long theId) throws Exception {
        throw new Exception("Not implemented yet!");
    }
}
