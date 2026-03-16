package com.wrogn.task.repository;

import com.wrogn.task.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long>
{

}
