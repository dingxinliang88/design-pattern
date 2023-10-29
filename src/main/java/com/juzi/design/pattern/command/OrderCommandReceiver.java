package com.juzi.design.pattern.command;

import cn.hutool.json.JSONUtil;
import com.juzi.design.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 接收者
 *
 * @author codejuzi
 */
@Component
public class OrderCommandReceiver {

    private final Logger logger = LoggerFactory.getLogger(OrderCommandReceiver.class);

    // 接收命令后执行
    public void action(Order order) {
        // 订单状态流转的相关操作
        switch (order.getOrderState()) {
            case ORDER_WAIT_PAY:
                logger.info("【创建订单】order = {}", JSONUtil.toJsonStr(order));
                logger.info("订单信息存入DB");
                break;
            case ORDER_WAIT_SEND:
                logger.info("【支付成功】order = {}", JSONUtil.toJsonStr(order));
                logger.info("订单信息存入DB");
                logger.info("通过MQ通知财务部门、物流部门");
                break;
            case ORDER_WAIT_RECEIVE:
                logger.info("【发货成功】order = {}", JSONUtil.toJsonStr(order));
                logger.info("订单信息存入DB");
                break;
            case ORDER_FINISH:
                logger.info("【收货成功】order = {}", JSONUtil.toJsonStr(order));
                logger.info("订单信息存入DB");
                break;
            default:
                throw new UnsupportedOperationException("Order State Error!");
        }
    }
}
