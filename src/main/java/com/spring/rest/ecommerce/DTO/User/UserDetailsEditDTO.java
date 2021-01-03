package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.UserDetail;

public class UserDetailsEditDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String zipCode;

    private String city;

    private String phoneNumber;

    public UserDetailsEditDTO() {}

    UserDetail toEntity(){
        UserDetail userDetails = new UserDetail(
                this.firstName,
                this.lastName,
                this.email,
                this.address,
                this.zipCode,
                this.city,
                this.phoneNumber
        );
        userDetails.setId(this.id);
        return userDetails;
    }
}
