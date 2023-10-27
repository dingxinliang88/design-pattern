package com.juzi.design.controller;

import com.juzi.design.pattern.state.deprecated.Order;
import com.juzi.design.service.OrderServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@Deprecated
@RequestMapping("/v1/order")
@RestController
public class OrderControllerV1 {

    @Autowired
    private OrderServiceV1 orderServiceV1;

    @PostMapping("/create")
    public Order createOrder(@RequestParam(value = "product_id") String productId) {
        return orderServiceV1.createOrder(productId);
    }

    @PostMapping("/pay")
    public Order payOrder(@RequestParam(value = "order_id") String orderId) {
        return orderServiceV1.payOrder(orderId);
    }

    @PostMapping("/send")
    public Order send(@RequestParam(value = "order_id") String orderId) {
        return orderServiceV1.send(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam(value = "order_id") String orderId) {
        return orderServiceV1.receive(orderId);
    }
}
