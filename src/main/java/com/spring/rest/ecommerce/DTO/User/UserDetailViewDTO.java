package com.spring.rest.ecommerce.DTO.User;

import com.spring.rest.ecommerce.entity.UserDetail;

public class UserDetailViewDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String zipCode;

    private String city;

    private String phoneNumber;

    public UserDetailViewDTO(UserDetail source){
        this.id = source.getId();
        this.firstName = source.getFirstName();
        this.lastName = source.getLastName();
        this.email = source.getEmail();
        this.address = source.getAddress();
        this.zipCode = source.getZipCode();
        this.city = source.getCity();
        this.phoneNumber = source.getPhoneNumber();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
