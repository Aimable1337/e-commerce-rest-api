package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByID(long theId);

    void save(User theUser);

    void deleteByID(long theId);
}
