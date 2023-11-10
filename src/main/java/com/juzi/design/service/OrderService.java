package com.juzi.design.service;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.juzi.design.pattern.command.OrderCommand;
import com.juzi.design.pattern.command.OrderCommandInvoker;
import com.juzi.design.pattern.decorator.OrderServiceDecorator;
import com.juzi.design.pattern.facade.PayFacade;
import com.juzi.design.pattern.state.recommend.OrderState;
import com.juzi.design.pattern.state.recommend.OrderStateChangeAction;
import com.juzi.design.pojo.Order;
import com.juzi.design.utils.RedisCommonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author codejuzi
 */
@Service
public class OrderService implements IOrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderState.class);

    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderState, OrderStateChangeAction, String> stateMachineRedisPersister;

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private PayFacade payFacade;


    @Override
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

    @Override
    public Order pay(String orderId) {
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

    @Override
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

    @Override
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

    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;

    @Value("${alipay.sign-type}")
    private String signType;

    @Autowired
    private OrderServiceDecorator orderServiceDecorator;

    @Value("${service.level:0}")
    private Integer serviceLevel;

    @Override
    public String alipayCallback(HttpServletRequest request) throws AlipayApiException {
        // 获取回调信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> paramsMap = request.getParameterMap();
        for (Map.Entry<String, String[]> param : paramsMap.entrySet()) {
            String name = param.getKey();
            String[] values = param.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes(StandardCharsets.UTF_8));
            params.put(name, valueStr);
        }
        // 验证签名，确保回调接口是支付宝平台触发的
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", signType);
        if (!signVerified) {
            throw new AlipayApiException("Callback Verify Failed");
        }

        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        float totalAmount = Float.parseFloat(new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        // 进行相关的业务操作
//        Order order = pay(outTradeNo);
        orderServiceDecorator.setOrderService(this);
        Order order = orderServiceDecorator.decoratorPay(outTradeNo, serviceLevel, totalAmount);

        return "支付成功页面跳转， 当前订单为:" + JSONUtil.toJsonStr(order);
    }

    @Override
    public String getPayUrl(String orderId, Float price, Integer payType) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        order.setPrice(price);
        return payFacade.pay(order, payType);
    }
}
