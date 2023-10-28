package com.juzi.design.pattern.observer.deprecated;

import javax.annotation.PostConstruct;

/**
 * 具体观察者
 *
 * @author codejuzi
 */
@Deprecated
public class ReceiveObserver extends AbstractObserver {
    @PostConstruct
    public void init() {
        ObserverConstants.OBSERVER_LIST.add(this);
    }

    @Override
    public void orderStateHandle(String orderId, String orderState) {
        if (!ObserverConstants.ORDER_FINISH.equals(orderState)) {
            return;
        }

        logger.info("监听到【收货成功，订单完成】");
    }
}
