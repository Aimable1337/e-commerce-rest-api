package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthoritiesRepository extends JpaRepository<UserAuthority, Long> {
}
