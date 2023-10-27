package com.juzi.design.pattern.state.deprecated;

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
    protected Order createOrder(String orderId, String productId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // pay order
    protected Order payOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // send order
    protected Order sendOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }

    // receive order
    protected Order receiveOrder(String orderId, OrderContext context) {
        throw new UnsupportedOperationException();
    }
    // endregion


}
