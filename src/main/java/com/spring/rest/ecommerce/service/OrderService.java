package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findOrderById(long theId);

    void save(Order theOrder);

    void deleteById(long theId);

}
