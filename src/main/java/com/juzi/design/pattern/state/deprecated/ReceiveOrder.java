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
public class ReceiveOrder extends AbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Override
    protected Order receiveOrder(String orderId, OrderContext context) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_RECEIVE)) {
            throw new UnsupportedOperationException("Order state isn't ORDER_WAIT_RECEIVE, but " + order.getState());
        }
        // 完成
        order.setState(ORDER_FINISH);
        // 删除缓存
        redisCommonProcessor.remove(orderId);
        return order;
    }
}
