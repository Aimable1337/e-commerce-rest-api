package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        if(userRepository.findAll().isEmpty())
            throw new NotFoundException("We have no users details :(");
        return userRepository.findAll();
    }

    @Override
    public User findByID(long theId) {
        if(userRepository.findById(theId).isPresent())
            return userRepository.findById(theId).get();
        throw new NotFoundException("User are not found by id: " + theId);
    }

    @Override
    public void save(User theUser) {
        if(theUser.getUserId() == 0 || userRepository.findById(theUser.getUserId()).isPresent()){
            userRepository.save(theUser);
        } else {
            throw new NotFoundException("User details with id: " + theUser.getUserId() + " does not exist");
        }
    }

    @Override
    public void deleteByID(long theId) {
        if(userRepository.findById(theId).isPresent()){
            userRepository.deleteById(theId);
        } else {
            throw new NotFoundException("User details with id: " + theId + " does not exist");
        }
    }
}
