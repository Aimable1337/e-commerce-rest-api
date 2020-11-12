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
        List<User> users = userService.findAll();
        if (!users.isEmpty()){
            return new ResponseEntity<>(
                    users,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/users/{theId}")
    public ResponseEntity<User> getUserById(@PathVariable long theId){
        User user = userService.findByID(theId);
        if (user != null){

            return new ResponseEntity<>(
                    user,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User theUser,
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
    public ResponseEntity<User> updateUser(@RequestBody User theUser,
                                                 HttpServletRequest request){
        if(userService.findByID(theUser.getUserID()) != null){
            userService.save(theUser);
            return new ResponseEntity<>(
                    theUser,
                    headerGenerator.getHeadersForSuccessPostMethod(request, theUser.getUserID()),
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                theUser,
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<String> deleteUsersById(@PathVariable long theId){
        if(userService.findByID(theId) != null){
            userService.deleteByID(theId);
            return new ResponseEntity<>(
                    "User deleted!",
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                "User doesn't exist!",
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND
        );
    }

}
