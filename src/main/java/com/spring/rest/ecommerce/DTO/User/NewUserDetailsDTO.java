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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
