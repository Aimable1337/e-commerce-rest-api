package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.UserDetails;

import java.util.List;

public interface UserService {

    List<UserDetails> findAll();

    UserDetails findByID(long theId);

    void save(UserDetails theUser);

    void deleteByID(long theId);
}
