package com.juzi.design.pattern.observer.deprecated;

import javax.annotation.PostConstruct;

/**
 * 具体观察者
 *
 * @author codejuzi
 */
@Deprecated
public class PayObserver extends AbstractObserver {
    @PostConstruct
    public void init() {
        ObserverConstants.OBSERVER_LIST.add(this);
    }

    @Override
    public void orderStateHandle(String orderId, String orderState) {
        if (!ObserverConstants.ORDER_WAIT_SEND.equals(orderState)) {
            return;
        }

        logger.info("监听到【订单支付成功，等待发货】");
    }
}
