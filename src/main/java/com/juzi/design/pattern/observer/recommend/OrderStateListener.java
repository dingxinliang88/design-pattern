package com.juzi.design.pattern.observer.recommend;

import com.juzi.design.pattern.command.OrderCommand;
import com.juzi.design.pattern.command.OrderCommandInvoker;
import com.juzi.design.pattern.state.recommend.OrderState;
import com.juzi.design.pattern.state.recommend.OrderStateChangeAction;
import com.juzi.design.pojo.Order;
import com.juzi.design.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * 监听器（观察者）
 *
 * @author codejuzi
 */
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    @OnTransition(source = {"ORDER_WAIT_PAY"}, target = {"ORDER_WAIT_SEND"})
    public boolean pay2Send(Message<OrderStateChangeAction> message) {
        // 获取订单，判断当前订单状态是否是待支付
        Order order = (Order) message.getHeaders().get("order");
        assert order != null;
        if (!OrderState.ORDER_WAIT_PAY.equals(order.getOrderState())) {
            throw new UnsupportedOperationException("Order State Error!");
        }

        // 支付成功后修改订单状态为待发货，并更新Redis缓存
        order.setOrderState(OrderState.ORDER_WAIT_SEND);
        redisCommonProcessor.set(order.getOrderId(), order);

        // 命令模式相关处理
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    @OnTransition(source = {"ORDER_WAIT_SEND"}, target = {"ORDER_WAIT_RECEIVE"})
    public boolean send2Receive(Message<OrderStateChangeAction> message) {
        // 获取订单，判断当前订单状态是否是待发货
        Order order = (Order) message.getHeaders().get("order");
        assert order != null;
        if (!OrderState.ORDER_WAIT_SEND.equals(order.getOrderState())) {
            throw new UnsupportedOperationException("Order State Error!");
        }

        // 支付成功后修改订单状态为待收货，并更新Redis缓存
        order.setOrderState(OrderState.ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(order.getOrderId(), order);

        // 命令模式相关处理
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    @OnTransition(source = {"ORDER_WAIT_RECEIVE"}, target = {"ORDER_FINISH"})
    public boolean receive2Finish(Message<OrderStateChangeAction> message) {
        // 获取订单，判断当前订单状态是否是待收货
        Order order = (Order) message.getHeaders().get("order");
        assert order != null;
        if (!OrderState.ORDER_WAIT_RECEIVE.equals(order.getOrderState())) {
            throw new UnsupportedOperationException("Order State Error!");
        }

        order.setOrderState(OrderState.ORDER_FINISH);
        redisCommonProcessor.remove(order.getOrderId());
        // 删除订单状态机
        redisCommonProcessor.remove(order.getOrderId() + "_STATE");

        // 命令模式相关处理
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

}
