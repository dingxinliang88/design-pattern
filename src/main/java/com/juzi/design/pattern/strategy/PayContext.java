package com.juzi.design.pattern.strategy;

import com.juzi.design.pojo.Order;

/**
 * 策略环境类（策略模式）
 * 具体产品类（工厂模式）
 *
 * @author codejuzi
 */
public class PayContext extends AbstractPayContext {

    private final PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    @Override
    public String execute(Order order) {
        return this.payStrategy.pay(order);
    }
}
