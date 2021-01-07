package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.UserDetailViewDTO;
import com.spring.rest.ecommerce.DTO.User.UserViewDTO;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserViewerImpl implements UserViewer{

    private final UserRepository userRepository;

    @Autowired
    public UserViewerImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<UserViewDTO> viewAll() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty())
            throw new NotFoundException("We have no users");

        return userList.stream()
                .map(UserViewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserViewDTO viewByID(long theId) {
        return userRepository.findById(theId)
                .map(UserViewDTO::new)
                .orElseThrow(() -> new NotFoundException("User with id: " + theId + " does not exist"));
    }

    @Override
    public UserViewDTO viewByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(UserViewDTO::new)
                .orElseThrow(() -> new NotFoundException("User with username: " + userName + " does not exist"));
    }

    @Override
    public UserDetailViewDTO viewUserDetailByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(User::getUserDetails)
                .map(UserDetailViewDTO::new)
                .orElseThrow(() -> new NotFoundException("User with username: " + userName + " does not exist"));
    }

    @Override
    public long getUserId(String userName) {
        return userRepository.getIdByName(userName).orElseThrow(
                () -> new NotFoundException("User with username: " + userName + " does not exist")
        );
    }
}
