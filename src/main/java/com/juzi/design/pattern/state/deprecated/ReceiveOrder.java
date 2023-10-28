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
public class ReceiveOrder extends AbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Override
    protected OrderV1 receiveOrder(String orderId, OrderContext context) {
        OrderV1 order = (OrderV1) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_RECEIVE)) {
            throw new UnsupportedOperationException("Order state isn't ORDER_WAIT_RECEIVE, but " + order.getState());
        }
        // 完成
        order.setState(ORDER_FINISH);
        // 删除缓存
        redisCommonProcessor.remove(orderId);

        // 通知观察者
        super.notifyObserver(orderId, ORDER_FINISH);
        return order;
    }
}
