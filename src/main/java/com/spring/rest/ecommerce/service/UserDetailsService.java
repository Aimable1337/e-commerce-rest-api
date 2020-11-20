package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.UserDetail;

import java.util.List;

public interface UserDetailsService {

    List<UserDetail> findAll();

    UserDetail findByID(long theId);

    void save(UserDetail theUser);

    void deleteByID(long theId);
}
