package com.juzi.design.controller;

import com.juzi.design.pojo.OrderV1;
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
    private OrderServiceV1 orderService;

    @PostMapping("/create")
    public OrderV1 createOrder(@RequestParam(value = "product_id") String productId) {
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public OrderV1 payOrder(@RequestParam(value = "order_id") String orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/send")
    public OrderV1 send(@RequestParam(value = "order_id") String orderId) {
        return orderService.send(orderId);
    }

    @PostMapping("/receive")
    public OrderV1 receive(@RequestParam(value = "order_id") String orderId) {
        return orderService.receive(orderId);
    }
}
