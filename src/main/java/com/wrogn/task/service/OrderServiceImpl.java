package com.wrogn.task.service;

import com.wrogn.task.entity.OrderEntity;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.OrderRepository;
import com.wrogn.task.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService
{

    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserRepository userRepository,OrderRepository orderRepository)
    {
        this.userRepository=userRepository;
        this.orderRepository=orderRepository;
    }

    @Override
    public void createOrder(Long userId, String orderName)
    {
        UserEntity user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        OrderEntity order=new OrderEntity();
        order.setOrderName(orderName);
        order.setUser(user);

        user.getOrders().add(order);
        userRepository.save(user);

    }
}
