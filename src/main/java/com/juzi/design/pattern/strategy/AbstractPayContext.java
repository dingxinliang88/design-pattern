package com.juzi.design.pattern.strategy;

import com.juzi.design.pojo.Order;

/**
 * 抽象产品类（工厂模式）
 *
 * @author codejuzi
 */
public abstract class AbstractPayContext {

    public abstract String execute(Order order);
}
