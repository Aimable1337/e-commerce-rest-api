package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserDetail;

public class UserViewDTO {

    private long userId;

    private String userName;

    private UserDetail userDetails;

    public UserViewDTO() {}

    public UserViewDTO(User source) {
        this.userId = source.getUserId();
        this.userName = source.getUserName();
        this.userDetails = source.getUserDetails();
    }

}
