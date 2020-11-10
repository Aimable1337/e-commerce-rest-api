package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    public UserServiceImpl(UserRepository theUserRepository){
        userRepository = theUserRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByID(long theId) {
        if (userRepository.findById(theId).isPresent()){
            return userRepository.findById(theId).get();
        } else {
            throw new RuntimeException("Did not found user by id - " + theId);
        }
    }

    @Override
    public void save(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public void deleteByID(long theId) {
        if (userRepository.findById(theId).isPresent()){
            userRepository.deleteById(theId);
        } else {
            throw new RuntimeException("Did not found user by id - " + theId);
        }
    }
}
