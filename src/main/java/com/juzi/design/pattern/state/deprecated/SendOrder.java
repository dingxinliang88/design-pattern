package com.juzi.design.pattern.state.deprecated;

import com.juzi.design.pojo.OrderV1;
import com.juzi.design.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 具体状态类
 *
 * @author codejuzi
 */
@Deprecated
@Component
public class SendOrder extends AbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private ReceiveOrder receiveOrder;

    @Override
    protected OrderV1 sendOrder(String orderId, OrderContext context) {
        OrderV1 order = (OrderV1) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_SEND)) {
            throw new UnsupportedOperationException("Order state isn't ORDER_WAIT_SEND, but " + order.getState());
        }
        // 签收
        order.setState(ORDER_WAIT_RECEIVE);
        // 更新缓存
        redisCommonProcessor.set(orderId, order);
        // 设置上下文对象当前状态
        // context.setCurrentState(receiveOrder);

        // 通知观察者
        super.notifyObserver(orderId, ORDER_WAIT_RECEIVE);
        return order;
    }
}
