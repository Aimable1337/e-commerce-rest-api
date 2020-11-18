package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
}
