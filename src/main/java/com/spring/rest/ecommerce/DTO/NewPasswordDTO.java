package com.spring.rest.ecommerce.DTO;

public class NewPasswordDTO {

    private String newPassword;

    private String oldPassword;

    public NewPasswordDTO() {
    }

    public NewPasswordDTO(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
