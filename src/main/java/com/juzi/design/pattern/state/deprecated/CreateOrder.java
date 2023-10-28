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
public class CreateOrder extends AbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private PayOrder payOrder;

    @Override
    protected OrderV1 createOrder(String orderId, String productId, OrderContext context) {
        // 创建订单对象，设置状态为ORDER_WAIT_PAY
        OrderV1 order = OrderV1.builder()
                .orderId(orderId)
                .productId(productId)
                .state(ORDER_WAIT_PAY)
                .build();
        // 新订单存入Redis，15min后失效
        redisCommonProcessor.setExpiredMinutes(orderId, order, 15);
        // 设置上下文对象当前状态为待支付
        // context.setCurrentState(payOrder);

        // 通知观察者
        super.notifyObserver(orderId, ORDER_WAIT_PAY);
        return order;
    }
}
