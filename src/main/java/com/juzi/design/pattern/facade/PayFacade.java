package com.juzi.design.pattern.facade;

import com.juzi.design.pattern.factory.PayContextFactory;
import com.juzi.design.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付门面类
 *
 * @author codejuzi
 */
@Component
public class PayFacade {

    @Autowired
    private PayContextFactory payContextFactory;

    public String pay(Order order, Integer payType) {
        return payContextFactory.getContext(payType).execute(order);
    }
}
