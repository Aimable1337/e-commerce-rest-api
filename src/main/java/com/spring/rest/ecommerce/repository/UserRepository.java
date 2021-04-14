package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.userName = ?1")
    Optional<User> findByUserName(String username);

    @Query("SELECT u.userId FROM User u where u.userName = ?1")
    Optional<Long> getIdByName(String userName);

    boolean existsByUserName(String userName);
}