package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.NewPasswordDTO;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;

public interface UserEditor {

    void changeMyUserName(String newUserName);

    void changeMyPassword(NewPasswordDTO newPasswordDTO);

    void changeMyDetails(UserDetail newUserDetails);

    void changeUsersAuthority(String userName, UserAuthority newAuthority);

    boolean checkIfOldPasswordIsValid(String oldPassword);

    void banUserById(long theId);

    void deleteByID(long theId);

}
