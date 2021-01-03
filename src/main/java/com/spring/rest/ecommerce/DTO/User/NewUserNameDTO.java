package com.spring.rest.ecommerce.DTO.User;

public class NewUserNameDTO {

    private String newUserName;

    public NewUserNameDTO() {
    }

    public NewUserNameDTO(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewUserName() {
        return newUserName;
    }

}
