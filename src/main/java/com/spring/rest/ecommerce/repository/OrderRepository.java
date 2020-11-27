package com.spring.rest.ecommerce.repository;

import com.spring.rest.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o from Order o where o.userId = ?1")
    List<Order> findAllOrdersByUserId(long userId);

}
