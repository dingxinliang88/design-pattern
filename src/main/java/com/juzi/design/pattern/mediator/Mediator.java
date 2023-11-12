package com.juzi.design.pattern.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体中介者
 *
 * @author codejuzi
 */
@Component
public class Mediator extends AbstractMediator {

    private final Logger logger = LoggerFactory.getLogger(Mediator.class);

    // 关联同事类
    // orderId - {buyer / payer - AbstractCustomer}
    public static Map<String, Map<String, AbstractCustomer>> customerInstances = new ConcurrentHashMap<>();

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {

        if (customer instanceof Buyer) {
            // 购买者
            AbstractCustomer buyer = customerInstances.get(orderId).get("buyer");
            logger.info("{} 找朋友代付，转发OrderId: {}, 到用户: {} 进行支付", buyer.customerName, orderId, targetCustomer);
        } else if (customer instanceof Payer) {
            // 支付者
            AbstractCustomer payer = customerInstances.get(orderId).get("payer");
            logger.info("{} 代付完成OrderId: {}, 通知 {}, 支付结果：{}", payer.customerName, orderId, targetCustomer, payResult);
            customerInstances.remove(orderId);
        }
    }
}
