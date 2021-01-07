package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.*;

public interface UserEditor {

    long register(NewUserDTO newUserDTO);

    void changeMyUserName(NewUserNameDTO newUserNameDTO, String userName);

    void changeMyPassword(NewPasswordDTO newPasswordDTO, String userName);

    void changeMyDetails(NewUserDetailsDTO newUserDetailsDTO, String userName);

    void changeUsersAuthority(long theId, NewUsersAuthorityDTO newAuthority);

    boolean checkIfOldPasswordIsValid(String oldPassword, String userPassword);

    void banUserById(long theId);

    void deleteByID(long theId);

}
