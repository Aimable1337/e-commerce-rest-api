package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;

public class NewUserDTO {

    private String userName;

    private String password;

    private String email;

    public User toEntity(){
       User user = new User();
       UserDetail detail = new UserDetail();
       UserAuthority authority = new UserAuthority(this.userName, "ROLE_USER");
       detail.setEmail(this.email);
       user.setUserName(this.userName);
       user.setPassword(this.password);
       user.setUserDetails(detail);
       user.setUserAuthority(authority);
       return user;
    }

}