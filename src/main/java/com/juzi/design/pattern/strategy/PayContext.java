package com.juzi.design.pattern.strategy;

import com.juzi.design.pojo.Order;

/**
 * 策略环境类
 *
 * @author codejuzi
 */
public class PayContext {

    private final PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    public String execute(Order order) {
        return this.payStrategy.pay(order);
    }
}
