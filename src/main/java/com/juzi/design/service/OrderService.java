package com.juzi.design.service;

import com.juzi.design.pattern.command.OrderCommand;
import com.juzi.design.pattern.command.OrderCommandInvoker;
import com.juzi.design.pattern.state.recommend.OrderState;
import com.juzi.design.pattern.state.recommend.OrderStateChangeAction;
import com.juzi.design.pojo.Order;
import com.juzi.design.utils.RedisCommonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

/**
 * @author codejuzi
 */
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderState.class);

    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderState, OrderStateChangeAction, String> stateMachineRedisPersister;

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    public Order createOrder(String productId) {
        String orderId = "order_" + productId;
        Order order = Order.builder()
                .orderId(orderId)
                .productId(productId)
                .orderState(OrderState.ORDER_WAIT_PAY)
                .build();
        redisCommonProcessor.setExpiredMinutes(orderId, order, 15);

        // 命令模式相关处理
        // 特殊处理的原因：订单创建状态没有被Spring状态机管理
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return order;
    }

    // TODO 待修改，有雷区
    public Order payOrder(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        // 订单状态变更消息Message，附带订单操作PAY
        Message<OrderStateChangeAction> message = MessageBuilder
                .withPayload(OrderStateChangeAction.PAY).setHeader("order", order).build();
        // 发送 Message 给Spring状态机
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;
    }

    public Order send(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        // 订单状态变更消息Message，附带订单操作SEND
        Message<OrderStateChangeAction> message = MessageBuilder
                .withPayload(OrderStateChangeAction.SEND).setHeader("order", order).build();
        // 发送 Message 给Spring状态机
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;
    }

    public Order receive(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        // 订单状态变更消息Message，附带订单操作RECEIVE
        Message<OrderStateChangeAction> message = MessageBuilder
                .withPayload(OrderStateChangeAction.RECEIVE).setHeader("order", order).build();
        // 发送 Message 给Spring状态机
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;
    }

    private boolean changeStateAction(Message<OrderStateChangeAction> message, Order order) {
        try {
            orderStateMachine.start();
            // 从Redis中读取状态机，缓存的Key为 orderId + _STATE
            stateMachineRedisPersister.restore(orderStateMachine, order.getOrderId() + "_STATE");
            // 将Message发送给OrderStateListener
            boolean res = orderStateMachine.sendEvent(message);
            // 将更改完的订单状态状态机存储到Redis
            stateMachineRedisPersister.persist(orderStateMachine, order.getOrderId() + "_STATE");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }

        return false;
    }

}
