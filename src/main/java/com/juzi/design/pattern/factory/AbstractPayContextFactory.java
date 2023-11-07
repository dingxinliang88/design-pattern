package com.juzi.design.pattern.factory;

/**
 * 抽象工厂类
 *
 * @author codejuzi
 */
public abstract class AbstractPayContextFactory<T> {

    public abstract T getContext(Integer payType);
}
