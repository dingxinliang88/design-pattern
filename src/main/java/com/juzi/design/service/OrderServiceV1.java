package com.juzi.design.service;

import com.juzi.design.pattern.state.deprecated.Order;
import com.juzi.design.pattern.state.deprecated.OrderContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author codejuzi
 */
@Deprecated
@Service
public class OrderServiceV1 {

    @Autowired
    private OrderContext orderContext;

    public Order createOrder(String productId) {
        String orderId = "order_" + productId;
        return orderContext.createOrder(orderId, productId);
    }

    public Order payOrder(String orderId) {
        return orderContext.payOrder(orderId);
    }

    public Order send(String orderId) {
        return orderContext.sendOrder(orderId);
    }

    public Order receive(String orderId) {
        return orderContext.receiveOrder(orderId);
    }
}
