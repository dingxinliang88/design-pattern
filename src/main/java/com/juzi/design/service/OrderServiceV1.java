package com.juzi.design.service;

import com.juzi.design.pojo.OrderV1;
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

    public OrderV1 createOrder(String productId) {
        String orderId = "order_" + productId;
        return orderContext.createOrder(orderId, productId);
    }

    public OrderV1 payOrder(String orderId) {
        return orderContext.payOrder(orderId);
    }

    public OrderV1 send(String orderId) {
        return orderContext.sendOrder(orderId);
    }

    public OrderV1 receive(String orderId) {
        return orderContext.receiveOrder(orderId);
    }
}
