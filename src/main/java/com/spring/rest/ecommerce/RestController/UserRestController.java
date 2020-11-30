package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
public class UserRestController {

    private final UserService userService;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public UserRestController(UserService userService,
                              ResponseMessageGenerator responseMessageGenerator,
                              HeaderGenerator headerGenerator) {
        this.userService = userService;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/show-details")
    public ResponseEntity<UserDetail> showMyDetails(HttpServletRequest request) {
        return new ResponseEntity<>(
                userService.findByUserName(request.getRemoteUser()).getUserDetails(),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @PutMapping("/change-username")
    public ResponseEntity<ResponseMessage> changeMyUserName(HttpServletRequest request, @RequestBody String newUserName) {
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userService.changeMyUserName(request, newUserName)),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseMessage> changeMyPassword(@RequestBody String oldPassword,
                                                            @RequestBody String newPassword) {
        userService.changeMyPassword(oldPassword, newPassword);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userService.getUserId()),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/change-details")
    public ResponseEntity<ResponseMessage> changeMyDetails(@RequestBody UserDetail newUserDetails) {
        userService.changeMyDetails(newUserDetails);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userService.getUserId()),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}