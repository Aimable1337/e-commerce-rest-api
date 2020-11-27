package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByID(long theId);

    User findByUserName(String userName);

    long getUserId();

    void save(User theUser);

    long changeMyUserName(HttpServletRequest request, String newUserName);

    void deleteByID(long theId);

    void changeMyPassword(String oldPassword, String newPassword);

    boolean checkIfOldPasswordIsValid(String oldPassword);

    void changeMyDetails(UserDetail newUserDetails);
}
