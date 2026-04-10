package com.wrogn.task.controller;

import com.wrogn.task.dto.response.ApiResponse;
import com.wrogn.task.dto.utiliy.ResponseUtil;
import com.wrogn.task.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService orderService;
    public OrderController(OrderService orderService)
    {
        this.orderService=orderService;
    }


    @PostMapping
    public  ResponseEntity<ApiResponse<String>> createOrder(@RequestParam Long userId, @RequestParam String orderName)
    {
        orderService.createOrder(userId,orderName);
        return ResponseEntity.ok(ResponseUtil.success("Order created",orderName));
    }

}
