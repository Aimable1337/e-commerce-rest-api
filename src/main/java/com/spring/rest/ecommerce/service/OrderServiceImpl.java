package com.spring.rest.ecommerce.service;

import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.entity.User;
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
    public List<Order> findAllOrdersByUserId(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id: " + userId + " not found")
        );
        if (orderRepository.findAllOrdersByUserId(user).isEmpty())
            throw new NotFoundException("User with id " + userId + " has no orders");
        return orderRepository.findAllOrdersByUserId(user);
    }

    @Override
    public List<Order> findMyOrders(String userName){
        List<Order> myOrders = findAllOrdersByUserId(userRepository.getIdByName(userName));
        if (!myOrders.isEmpty()){
            return findAllOrdersByUserId(userRepository.getIdByName(userName));
        } else {
            throw new NotFoundException("You have no orders");
        }
    }

    @Override
    public Order findOrderById(long theId) {
            return orderRepository.findById(theId).orElseThrow(
                    () -> new NotFoundException("Order with id: " + theId + " does not exist")
            );
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
        Order order = new Order(LocalDate.now(), userRepository.findByUserName(request.getRemoteUser()), products);
        orderRepository.save(order);
        return order.getOrderId();
    }
}
