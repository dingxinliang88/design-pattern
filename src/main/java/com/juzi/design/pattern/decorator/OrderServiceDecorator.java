package com.juzi.design.pattern.decorator;

import com.juzi.design.mq.MQConstant;
import com.juzi.design.pojo.Order;
import com.juzi.design.pojo.Products;
import com.juzi.design.repo.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author codejuzi
 */
@Component
public class OrderServiceDecorator extends AbstractOrderServiceDecorator {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceDecorator.class);

    @Value("${delay.service.time:1800000}")
    private String delayServiceTime;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected void updateScoreAndSendRedPaper(String productId, Integer serviceLevel, Float price) {
        switch (serviceLevel) {
            case 0: // 正常处理
                logger.info("ServiceLevel = 1 => Handle");
                // 更新积分
                int score = Math.round(price) / 100;
                logger.info("ServiceLevel = 0 => score = {}", score);
                // 发放红包
                Products products = productsRepository.findByProductId(productId);
                if (products != null && products.getSendRedBag() == 1) {
                    logger.info("ServiceLevel = 0 => Send Red Bag, products id : {}", productId);
                }
                break;
            case 1: // 延迟服务
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setExpiration(delayServiceTime);
                Message message = new Message(productId.getBytes(StandardCharsets.UTF_8), messageProperties);
                rabbitTemplate.send(MQConstant.NORMAL_EXCHANGE, MQConstant.NORMAL_ROUTING_KEY, message);
                logger.info("ServiceLevel = 1 => Delay Handle");
                break;
            case 2: // 暂停服务
                logger.info("ServiceLevel = 2 => Stop Handle");
                break;
            default:
                throw new UnsupportedOperationException("Service Level Is InValid!");
        }
    }

    // 将pay方法和updateScoreAndSendRedPaper
    public Order decoratorPay(String orderId, Integer serviceLevel, Float price) {
        Order order = super.pay(orderId);

        // 新的逻辑：更新积分，发送用户红包
        try {
            this.updateScoreAndSendRedPaper(order.getProductId(), serviceLevel, price);
        } catch (Exception ignored) {
            // 重试机制，此处积分更新不能影响支付主流程
        }
        return order;
    }
}
