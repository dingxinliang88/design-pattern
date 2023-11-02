package com.juzi.design.pattern.strategy;

import com.juzi.design.pojo.Order;

/**
 * 抽象策略类
 *
 * @author codejuzi
 */
public interface PayStrategyInterface {

    /**
     * 支付
     *
     * @param order 订单
     */
    String pay(Order order);
}
