package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/user-panel")
public class AdminUserPanelRestController {

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public AdminUserPanelRestController(UserService userService,
                                        HeaderGenerator headerGenerator,
                                        ResponseMessageGenerator responseMessageGenerator) {
        this.userService = userService;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(
                userService.findAll(),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/{theId}")
    public ResponseEntity<User> getSingleUser(@PathVariable long theId) {
        return new ResponseEntity<>(
                userService.findByID(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) {
        user.setUserId(0);
        userService.save(user);
        return new ResponseEntity<>(
                user,
                headerGenerator.getHeadersForSuccessPostMethod(request, user.getUserId()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user, HttpServletRequest request) {
        userService.save(user);
        return new ResponseEntity<>(
                user,
                headerGenerator.getHeadersForSuccessPostMethod(request, user.getUserId()),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/change-authority/{theId}")
    public ResponseEntity<ResponseMessage> changeAuthorities(@PathVariable long theId,
                                                             @RequestBody UserAuthority newAuthority,
                                                             HttpServletRequest request) {
        User user = userService.findByID(theId);
        user.setUserAuthority(newAuthority);
        userService.save(user);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(theId),
                headerGenerator.getHeadersForSuccessPostMethod(request, user.getUserId()),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/ban-user/{theId}")
    public ResponseEntity<ResponseMessage> banUser(@PathVariable long theId, HttpServletRequest request) {
        User user = userService.findByID(theId);
        user.setEnabled(false);
        userService.save(user);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessBanMethod(theId),
                headerGenerator.getHeadersForSuccessPostMethod(request, user.getUserId()),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable long theId) {
        userService.deleteByID(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessDeleteMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}
