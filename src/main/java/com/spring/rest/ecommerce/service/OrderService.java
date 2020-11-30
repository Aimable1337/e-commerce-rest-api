package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findOrderById(long theId);

    List<Order> findAllOrdersByUserId(long userId);

    List<Order> findMyOrders(HttpServletRequest request);

    void save(Order theOrder);

    void deleteById(long theId);

    long createOrder(List<Product> products, HttpServletRequest request);
}
