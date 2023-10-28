package com.juzi.design.pattern.observer.deprecated;

import javax.annotation.PostConstruct;

/**
 * 具体观察者
 *
 * @author codejuzi
 */
@Deprecated
public class SendObserver extends AbstractObserver {
    @PostConstruct
    public void init() {
        ObserverConstants.OBSERVER_LIST.add(this);
    }

    @Override
    public void orderStateHandle(String orderId, String orderState) {
        if (!ObserverConstants.ORDER_WAIT_RECEIVE.equals(orderState)) {
            return;
        }

        logger.info("监听到【发货成功，等待收货】");
    }
}
