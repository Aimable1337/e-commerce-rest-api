package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserDetail;

public class UserViewDTO {

    private long userId;

    private String userName;

    private UserDetail userDetails;

    public UserViewDTO(User source) {
        this.userId = source.getUserId();
        this.userName = source.getUserName();
        this.userDetails = source.getUserDetails();
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UserDetail getUserDetails() {
        return userDetails;
    }
}
