package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.User.UserViewDTO;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserService;
import com.spring.rest.ecommerce.service.UserViewer;
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

    private final UserViewer userViewer;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public AdminUserPanelRestController(UserService userService,
                                        UserViewer userViewer,
                                        HeaderGenerator headerGenerator,
                                        ResponseMessageGenerator responseMessageGenerator) {
        this.userService = userService;
        this.userViewer = userViewer;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserViewDTO>> getAllUsers() {
        return new ResponseEntity<>(
                userViewer.viewAll(),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/id={theId}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable long theId) {
        return new ResponseEntity<>(
                userViewer.viewByID(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/name={userName}")
    public ResponseEntity<UserViewDTO> getUserByUserName(@PathVariable String userName) {
        return new ResponseEntity<>(
                userViewer.viewByUserName(userName),
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
        newAuthority.setId(user.getUserAuthority().getId());
        user.setUserAuthority(newAuthority);
        userService.save(user);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(theId),
                headerGenerator.getHeadersForSuccessPostMethod(request, user.getUserId()),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/ban-user/{theId}")
    public ResponseEntity<ResponseMessage> banUser(@PathVariable long theId) {
        userService.banUserById(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessBanMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
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