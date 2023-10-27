package com.juzi.design.pattern.state.deprecated;

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
public class PayOrder extends AbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private SendOrder sendOrder;

    @Override
    protected Order payOrder(String orderId, OrderContext context) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_PAY)) {
            throw new UnsupportedOperationException("Order state isn't ORDER_WAIT_PAY, but " + order.getState());
        }
        // 支付
        order.setState(ORDER_WAIT_SEND);
        // 更新缓存
        redisCommonProcessor.set(orderId, order);
        // 设置上下文对象当前状态
        // context.setCurrentState(sendOrder);
        return order;
    }
}
