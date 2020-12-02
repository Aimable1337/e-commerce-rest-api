package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.exception.OldPasswordInvalid;
import com.spring.rest.ecommerce.exception.UsernameAlreadyExistException;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        if(userRepository.findAll().isEmpty())
            throw new NotFoundException("We have no users :(");
        return userRepository.findAll();
    }

    @Override
    public User findByID(long theId) {
        if(userRepository.findById(theId).isPresent())
            return userRepository.findById(theId).get();
        throw new NotFoundException("User is not found by id: " + theId);
    }

    @Override
    public User findByUserName(String userName) {
        if(userRepository.findByUserName(userName) != null)
            return userRepository.findByUserName(userName);
        throw new NotFoundException("User is not found by name: " + userName);
    }

    @Override
    public long getUserId() {
        return findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
    }

    @Override
    public void save(User theUser) {
        if(theUser.getUserId() == 0 || userRepository.findById(theUser.getUserId()).isPresent()){
            userRepository.save(theUser);
        } else {
            throw new NotFoundException("User with id: " + theUser.getUserId() + " does not exist");
        }
    }

    @Override
    public void register(User newUser){
        String password ="{bcrypt}" + new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(password);
        newUser.setUserId(0);
        userRepository.save(newUser);
    }

    @Override
    public long changeMyUserName(HttpServletRequest request, String newUserName) {
        User user = findByUserName(request.getRemoteUser());
        if(userRepository.findByUserName(newUserName) == null){
            user.setUserName(newUserName);
            user.getUserAuthority().setUsername(newUserName);
            userRepository.save(user);
        } else {
            throw new UsernameAlreadyExistException("User with username: " + newUserName + " already exist");
        }
        return user.getUserId();
    }

    @Override
    public void deleteByID(long theId) {
        if(userRepository.findById(theId).isPresent()){
            userRepository.deleteById(theId);
        } else {
            throw new NotFoundException("User details with id: " + theId + " does not exist");
        }
    }

    @Override
    public void changeMyPassword(String oldPassword, String newPassword) {
        if(checkIfOldPasswordIsValid(oldPassword)){
            User user = findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        } else {
            throw new OldPasswordInvalid("Old password is invalid");
        }
    }

    @Override
    public boolean checkIfOldPasswordIsValid(String oldPassword) {
        User user = findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        String userPassword = user.getPassword().replace("{bcrypt}", "");
        return new BCryptPasswordEncoder().matches(oldPassword, userPassword);
    }

    @Override
    public void changeMyDetails(UserDetail newUserDetails) {
      newUserDetails.setUserID(getUserId());
      User user = findByID(getUserId());
      user.setUserDetails(newUserDetails);
      userRepository.save(user);
    }

    @Override
    public void banUserById(long theId) {
        User user = findByID(theId);
        user.setEnabled(false);
        userRepository.save(user);
    }
}
