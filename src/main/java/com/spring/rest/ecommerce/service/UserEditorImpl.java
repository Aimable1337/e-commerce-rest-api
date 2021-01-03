package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.DTO.User.NewPasswordDTO;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.exception.OldPasswordInvalid;
import com.spring.rest.ecommerce.exception.UsernameAlreadyExistException;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEditorImpl implements UserEditor {

    private final UserRepository userRepository;

    @Autowired
    public UserEditorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void changeMyUserName(String newUserName) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        if (userRepository.findByUserName(newUserName).isPresent()) {
            user.setUserName(newUserName);
            userRepository.save(user);
        } else {
            throw new UsernameAlreadyExistException("User with username: " + newUserName + " already exist.");
        }
    }

    @Override
    public void changeMyPassword(NewPasswordDTO newPasswordDTO) {
        if (checkIfOldPasswordIsValid(newPasswordDTO.getOldPassword())){
            User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                    () -> new NotFoundException("User not found in data base")
            );
            user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(newPasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new OldPasswordInvalid("Old password is invalid");
        }
    }

    @Override
    public void changeMyDetails(UserDetail newUserDetails) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        newUserDetails.setId(user.getUserId());
        user.setUserDetails(newUserDetails);
        userRepository.save(user);
    }

    @Override
    public void changeUsersAuthority(String userName, UserAuthority newAuthority) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        user.setUserAuthority(newAuthority);
    }

    @Override
    public boolean checkIfOldPasswordIsValid(String oldPassword) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new NotFoundException("User not found in data base")
        );
        String userPassword = user.getPassword().replace("{bcrypt}", "");
        return new BCryptPasswordEncoder().matches(oldPassword, userPassword);
    }

    @Override
    public void banUserById(long theId) {

    }

    @Override
    public void deleteByID(long theId) {

    }
}
