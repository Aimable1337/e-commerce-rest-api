package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.exception.UserNotFoundException;
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
        if(userRepository.findById(theId).isPresent()){
            return userRepository.findById(theId).get();
        } else {
            throw new UserNotFoundException("User is not found by id: " + theId);
        }
    }

    @Override
    public void save(User theUser) {
        if(theUser.getUserID() == 0 || userRepository.findById(theUser.getUserID()).isPresent()){
            userRepository.save(theUser);
        } else {
            throw new UserNotFoundException("User with id: " + theUser.getUserID() + " does not exist");
        }
    }

    @Override
    public void deleteByID(long theId) {
        if(userRepository.findById(theId).isPresent()){
            userRepository.deleteById(theId);
        } else {
            throw new UserNotFoundException("User with id: " + theId + " does not exist");
        }
    }
}
