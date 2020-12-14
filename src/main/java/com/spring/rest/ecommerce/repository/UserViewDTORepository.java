package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.DTO.UserViewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserViewDTORepository extends JpaRepository<UserViewDTO, Long> {

    @Query("SELECT v FROM UserViewDTO v where v.userName = ?1")
    Optional<UserViewDTO> findByUserName(String userName);

}