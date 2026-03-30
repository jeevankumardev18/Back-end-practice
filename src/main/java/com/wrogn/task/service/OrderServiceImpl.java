package com.wrogn.task.service;

import com.wrogn.task.entity.OrderEntity;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.OrderRepository;
import com.wrogn.task.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService
{

    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private static final Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(UserRepository userRepository,OrderRepository orderRepository)
    {
        this.userRepository=userRepository;
        this.orderRepository=orderRepository;
    }

    @Transactional
    @Override
    public void createOrder(Long userId, String orderName)
    {
        logger.info("Creating order for user: {}",userId);
        UserEntity user=userRepository.findById(userId)

                .orElseThrow(()->
                {
                    logger.error("Order creation failed for user {}",userId);
                    return new ResourceNotFoundException("User Not Found");
                });

        OrderEntity order=new OrderEntity();
        order.setOrderName(orderName);
        logger.info("Order name: {}",orderName);
        order.setUser(user);
        user.getOrders().add(order);
        userRepository.save(user);

        logger.info("Order created successfully for user: {}",userId);


    }
}
