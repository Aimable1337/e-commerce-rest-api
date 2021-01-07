package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.UserDetailViewDTO;
import com.spring.rest.ecommerce.DTO.User.UserViewDTO;

import java.util.List;

public interface UserViewer {

    List<UserViewDTO> viewAll();

    UserViewDTO viewByID(long theId);

    UserViewDTO viewByUserName(String userName);

    UserDetailViewDTO viewUserDetailByUserName(String userName);

    long getUserId(String userName);

}
