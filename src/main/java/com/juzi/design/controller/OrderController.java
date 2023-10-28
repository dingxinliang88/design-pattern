package com.juzi.design.controller;

import com.juzi.design.pojo.Order;
import com.juzi.design.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestParam(value = "product_id") String productId) {
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public Order payOrder(@RequestParam(value = "order_id") String orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/send")
    public Order send(@RequestParam(value = "order_id") String orderId) {
        return orderService.send(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam(value = "order_id") String orderId) {
        return orderService.receive(orderId);
    }

}
