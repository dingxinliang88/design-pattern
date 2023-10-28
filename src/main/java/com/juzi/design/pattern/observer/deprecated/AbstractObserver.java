package com.juzi.design.pattern.observer.deprecated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象观察者
 *
 * @author codejuzi
 */
@Deprecated
public abstract class AbstractObserver {

    protected final Logger logger = LoggerFactory.getLogger(AbstractObserver.class);

    public abstract void orderStateHandle(String orderId, String orderState);
}
