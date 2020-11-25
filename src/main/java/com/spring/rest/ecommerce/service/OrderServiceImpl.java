package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.exception.NotFoundException;
import com.spring.rest.ecommerce.repository.OrderRepository;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> findAll() {
        if (orderRepository.findAll().isEmpty())
            throw new NotFoundException("We have no orders");
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(long theId) {
        if(orderRepository.findById(theId).isPresent())
            return orderRepository.findById(theId).get();
        throw new NotFoundException("Order with id: " + theId + " does not exist");
    }

    @Override
    public void save(Order theOrder) {
        if(orderRepository.findById(theOrder.getOrderId()).isPresent() || theOrder.getOrderId() == 0){
            orderRepository.save(theOrder);
        } else {
            throw new NotFoundException("Order with id: " + theOrder.getOrderId() + " does not exist");
        }
    }

    @Override
    public void deleteById(long theId) {
        if(orderRepository.findById(theId).isPresent()){
            orderRepository.deleteById(theId);
        } else {
            throw new NotFoundException("Order with id: " + theId + " does not exist");
        }
    }

    @Override
    public long createOrder(List<Product> products, HttpServletRequest request) {
        Order order = new Order(0, LocalDate.now(), userRepository.findByUserName(request.getRemoteUser()), products);
        orderRepository.save(order);
        return order.getOrderId();
    }
}
