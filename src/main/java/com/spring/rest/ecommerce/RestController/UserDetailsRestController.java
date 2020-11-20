package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user-details-api")
public class UserDetailsRestController {

    private final UserDetailsService userDetailsService;

    private final HeaderGenerator headerGenerator;

    @Autowired
    public UserDetailsRestController(UserDetailsService userDetailService, HeaderGenerator headerGenerator) {
        this.userDetailsService = userDetailService;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/user-details")
    public ResponseEntity<List<UserDetail>> getUserDetails(){
            return new ResponseEntity<>(
                    userDetailsService.findAll(),
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @GetMapping("/user-details/{theId}")
    public ResponseEntity<UserDetail> getUserDetailsById(@PathVariable long theId){
            return new ResponseEntity<>(
                    userDetailsService.findByID(theId),
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
    }

    @PostMapping("/user-details")
    public ResponseEntity<UserDetail> createUserDetails(@RequestBody UserDetail theUser,
                                                        HttpServletRequest request){
        theUser.setUserID(0);
        userDetailsService.save(theUser);
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserID()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/user-details")
    public ResponseEntity<UserDetail> updateUserDetails(@RequestBody UserDetail theUser,
                                                        HttpServletRequest request){
            userDetailsService.save(theUser);
            return new ResponseEntity<>(
                    theUser,
                    headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserID()),
                    HttpStatus.ACCEPTED
            );
    }

    @DeleteMapping("/user-details/{theId}")
    public ResponseEntity<String> deleteUserDetailsById(@PathVariable long theId){
            userDetailsService.deleteByID(theId);
            return new ResponseEntity<>(
                    "User deleted!",
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.ACCEPTED
            );
    }
}
