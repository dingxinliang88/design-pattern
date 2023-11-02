package com.juzi.design.pattern.strategy;

import com.juzi.design.pojo.Order;

/**
 * 微信支付策略类
 *
 * @author codejuzi
 */
public class WechatPayStrategy implements PayStrategyInterface {
    @Override
    public String pay(Order order) {
        return null;
    }
}
