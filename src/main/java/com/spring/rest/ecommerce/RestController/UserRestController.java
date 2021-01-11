package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.User.NewPasswordDTO;
import com.spring.rest.ecommerce.DTO.User.NewUserDetailsDTO;
import com.spring.rest.ecommerce.DTO.User.NewUserNameDTO;
import com.spring.rest.ecommerce.DTO.User.UserDetailViewDTO;
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

@RestController
@RequestMapping("/account")
public class UserRestController {

    private final UserEditor userEditor;

    private final UserViewer userViewer;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public UserRestController(UserEditor userEditor,
                              UserViewer userViewer,
                              ResponseMessageGenerator responseMessageGenerator,
                              HeaderGenerator headerGenerator) {
        this.userEditor = userEditor;
        this.userViewer = userViewer;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @GetMapping("/show-details")
    public ResponseEntity<UserDetailViewDTO> showMyDetails(HttpServletRequest request) {
        return new ResponseEntity<>(
                userViewer.viewUserDetailByUserName(request.getRemoteUser()),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.OK
        );
    }

    @PutMapping("/change-username")
    public ResponseEntity<ResponseMessage> changeMyUserName(HttpServletRequest request, @RequestBody NewUserNameDTO newUserName) {
        userEditor.changeMyUserName(newUserName, request.getRemoteUser());
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userViewer.getUserId(newUserName.getNewUserName())),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseMessage> changeMyPassword(HttpServletRequest request, @RequestBody NewPasswordDTO newPasswordDTO) {
        userEditor.changeMyPassword(newPasswordDTO, request.getRemoteUser());
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userViewer.getUserId(request.getRemoteUser())),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/change-details")
    public ResponseEntity<ResponseMessage> changeMyDetails(HttpServletRequest request, @RequestBody NewUserDetailsDTO newUserDetailsDTO) {
        userEditor.changeMyDetails(newUserDetailsDTO, request.getRemoteUser());
        return new ResponseEntity<>(
                responseMessageGenerator.getResponseForSuccessPutMethod(userViewer.getUserId(request.getRemoteUser())),
                headerGenerator.getHeadersForSuccessGetMethod(),
                HttpStatus.ACCEPTED
        );
    }
}