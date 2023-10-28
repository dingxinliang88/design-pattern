package com.juzi.design.pattern.state.deprecated;

import com.juzi.design.pattern.observer.deprecated.AbstractObserver;
import com.juzi.design.pattern.observer.deprecated.ObserverConstants;
import com.juzi.design.pojo.OrderV1;

import java.util.List;

/**
 * State抽象状态对象
 *
 * @author codejuzi
 */
@Deprecated
public abstract class AbstractOrderState {

    // region state definition
    // wait for pay => send => receive => finish
    protected final String ORDER_WAIT_PAY = "ORDER_WAIT_PAY";
    protected final String ORDER_WAIT_SEND = "ORDER_WAIT_SEND";
    protected final String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE";
    protected final String ORDER_FINISH = "ORDER_FINISH";

    // endregion

    // region order function

    // create order
    protected OrderV1 createOrder(String orderId, String productId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // pay order
    protected OrderV1 payOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // send order
    protected OrderV1 sendOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // receive order
    protected OrderV1 receiveOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }
    // endregion

    // region observer function
    protected final List<AbstractObserver> observerList = ObserverConstants.OBSERVER_LIST;

    // add
    public void addObserver(AbstractObserver observer) {
        observerList.add(observer);
    }

    // remove
    public void removeObserver(AbstractObserver observer) {
        observerList.remove(observer);
    }

    // notify observers
    public void notifyObserver(String orderId, String orderState) {
        for (AbstractObserver observer : observerList) {
            observer.orderStateHandle(orderId, orderState);
        }
    }

    // endregion


}
