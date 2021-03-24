package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.*;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.exception.OldPasswordInvalid;
import com.spring.rest.ecommerce.exception.UsernameAlreadyExistException;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEditorImpl implements UserEditor {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserEditorImpl(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public long register(NewUserDTO newUserDTO) {
        User newUser = newUserDTO.toEntity();
        newUser.setUserId(0);
        newUser.setEnabled(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return newUser.getUserId();
    }

    @Override
    public void changeMyUserName(NewUserNameDTO newUserNameDTO, String userName) {
        if(userRepository.findByUserName(newUserNameDTO.getNewUserName()).isPresent()){
            throw new UsernameAlreadyExistException("User with username: " + newUserNameDTO.getNewUserName() + " already exist.");
        }
        userRepository.findByUserName(userName).ifPresent(user -> {
                user.setUserName(newUserNameDTO.getNewUserName());
                userRepository.save(user);
            }
        );
    }

    @Override
    public void changeMyPassword(NewPasswordDTO newPasswordDTO, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        if (checkIfOldPasswordIsValid(newPasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new OldPasswordInvalid("Old password is invalid");
        }
    }

    @Override
    public void changeMyDetails(NewUserDetailsDTO newUserDetailsDTO, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        user.setUserDetails(newUserDetailsDTO.toEntity());
        userRepository.save(user);
    }

    @Override
    public void changeUsersAuthority(long theId, NewUsersAuthorityDTO newAuthority) {
        User user = userRepository.findById(theId).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        user.setUserAuthority(
                newAuthority.ChangeAuthority(user.getUserAuthority())
        );
        userRepository.save(user);
    }

    @Override
    public boolean checkIfOldPasswordIsValid(String oldPassword, String userPassword) {
        return passwordEncoder.matches(oldPassword, userPassword);
    }

    @Override
    public void banUserById(long theId) {
        User user = userRepository.findById(theId).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void deleteByID(long theId) {
        userRepository.findById(theId).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        userRepository.deleteById(theId);
    }

}
