package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.DTO.User.NewUserDTO;
import com.spring.rest.ecommerce.headers.HeaderGenerator;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import com.spring.rest.ecommerce.service.UserEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
public class RegisterRestController {

    private final UserEditor userEditor;

    private final HeaderGenerator headerGenerator;

    private final ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    public RegisterRestController(UserEditor userEditor,
                                  ResponseMessageGenerator responseMessageGenerator,
                                  HeaderGenerator headerGenerator){
        this.userEditor = userEditor;
        this.responseMessageGenerator = responseMessageGenerator;
        this.headerGenerator = headerGenerator;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody NewUserDTO newUserDTO, HttpServletRequest request) {
        long newId = userEditor.register(newUserDTO);
        return new ResponseEntity<>(
                responseMessageGenerator.getMessageForSuccessUserRegistration(newId),
                headerGenerator.getHeadersForSuccessPostMethod(request.getRequestURI(), newId),
                HttpStatus.CREATED
        );
    }
}