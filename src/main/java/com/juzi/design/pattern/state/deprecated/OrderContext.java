package com.juzi.design.pattern.state.deprecated;

import com.juzi.design.pojo.OrderV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author codejuzi
 */
@Deprecated
@Component
public class OrderContext {

    /*private AbstractOrderState currentState;

    public void setCurrentState(AbstractOrderState currentState) {
        this.currentState = currentState;
    }*/

    // 初始状态
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private PayOrder payOrder;
    @Autowired
    private SendOrder sendOrder;
    @Autowired
    private ReceiveOrder receiveOrder;

    public OrderV1 createOrder(String orderId, String productId) {
        // this.currentState = createOrder;
        // return currentState.createOrder(orderId, productId, this);
        return createOrder.createOrder(orderId, productId, this);
    }

    public OrderV1 payOrder(String orderId) {
        // return currentState.payOrder(orderId, this);
        return payOrder.payOrder(orderId, this);
    }

    public OrderV1 sendOrder(String orderId) {
        // return currentState.sendOrder(orderId, this);
        return sendOrder.sendOrder(orderId, this);
    }

    public OrderV1 receiveOrder(String orderId) {
        // return currentState.receiveOrder(orderId, this);
        return receiveOrder.receiveOrder(orderId, this);
    }
}

