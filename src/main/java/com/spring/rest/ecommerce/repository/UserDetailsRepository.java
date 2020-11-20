package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetail, Long> {
}
