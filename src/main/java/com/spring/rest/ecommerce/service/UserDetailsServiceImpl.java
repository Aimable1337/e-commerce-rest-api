package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserDetailsRepository userDetailRepository;

    public UserDetailsServiceImpl(UserDetailsRepository theUserRepository){
        userDetailRepository = theUserRepository;
    }

    @Override
    public List<UserDetail> findAll() {
        if(userDetailRepository.findAll().isEmpty())
            throw new NotFoundException("We have no users details :(");
        return userDetailRepository.findAll();
    }

    @Override
    public UserDetail findByID(long theId) {
            return userDetailRepository.findById(theId).orElseThrow(
                    () -> new NotFoundException("User details are not found by id: " + theId)
            );
    }

    @Override
    public void save(UserDetail theUserDetail) {
        if(theUserDetail.getUserID() == 0 || userDetailRepository.findById(theUserDetail.getUserID()).isPresent()){
            userDetailRepository.save(theUserDetail);
        } else {
            throw new NotFoundException("User details with id: " + theUserDetail.getUserID() + " does not exist");
        }
    }

    @Override
    public void deleteByID(long theId) {
        if(userDetailRepository.findById(theId).isPresent()){
            userDetailRepository.deleteById(theId);
        } else {
            throw new NotFoundException("User details with id: " + theId + " does not exist");
        }
    }
}
