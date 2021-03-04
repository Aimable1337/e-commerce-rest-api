package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.UserDetail;

public class NewUserDetailsDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String zipCode;

    private String city;

    private String phoneNumber;

    public NewUserDetailsDTO() {}

    public UserDetail toEntity(){
        return new UserDetail(
                this.firstName,
                this.lastName,
                this.email,
                this.address,
                this.zipCode,
                this.city,
                this.phoneNumber
        );
    }
}
