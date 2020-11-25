package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.userName = ?1")
    User findByUserName(String username);

}
