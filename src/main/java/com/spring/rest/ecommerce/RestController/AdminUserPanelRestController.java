package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.User.NewUserDTO;
import com.spring.rest.ecommerce.DTO.User.NewUsersAuthorityDTO;
import com.spring.rest.ecommerce.DTO.User.UserViewDTO;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserEditor;
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

    private final UserEditor userEditor;

    private final UserViewer userViewer;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public AdminUserPanelRestController(UserEditor userEditor,
                                        UserViewer userViewer,
                                        HeaderGenerator headerGenerator,
                                        ResponseMessageGenerator responseMessageGenerator) {
        this.userEditor = userEditor;
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
    public ResponseEntity<ResponseMessage> createUser(@RequestBody NewUserDTO newUserDTO, HttpServletRequest request) {
        long id = userEditor.register(newUserDTO);
        return new ResponseEntity<>(
                responseMessageGenerator.getMessageForSuccessUserRegistration(id),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), id),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/change-authority/{theId}")
    public ResponseEntity<ResponseMessage> changeAuthorities(@PathVariable long theId,
                                                             @RequestBody NewUsersAuthorityDTO newAuthority,
                                                             HttpServletRequest request) {
        userEditor.changeUsersAuthority(theId, newAuthority);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(theId),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), theId),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/ban-user/{theId}")
    public ResponseEntity<ResponseMessage> banUser(@PathVariable long theId) {
        userEditor.banUserById(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessBanMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/users/{theId}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable long theId) {
        userEditor.deleteByID(theId);
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessDeleteMethod(theId),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}