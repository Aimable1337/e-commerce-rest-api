package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.UserViewDTO;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.UserViewDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserViewerImpl implements UserViewer{

    final private UserViewDTORepository userViewDTORepository;

    @Autowired
    public UserViewerImpl(UserViewDTORepository userViewDTORepository){
        this.userViewDTORepository = userViewDTORepository;
    }

    @Override
    public List<UserViewDTO> viewAll() {
        if (!userViewDTORepository.findAll().isEmpty())
            return userViewDTORepository.findAll();
        throw new NotFoundException("We have no users.");
    }

    @Override
    public UserViewDTO viewByID(long theId) {
        return userViewDTORepository.findById(theId).orElseThrow(
                () -> new NotFoundException("User with id: " + theId + " does not exist.")
        );
    }

    @Override
    public UserViewDTO viewByUserName(String userName) {
        return userViewDTORepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("User with username " + userName + " does not exist.")
        );
    }
}
