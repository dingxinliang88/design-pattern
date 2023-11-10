package com.juzi.design.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 原生RabbitMQ初始化
 *
 * @author codejuzi
 */
public class MQInit {

    private static final Logger logger = LoggerFactory.getLogger(MQInit.class);

    public static void main(String[] args) {
        // 1、创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2、设置参数
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        // 新建连接
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();

            // 创建普通交换机
            channel.exchangeDeclare(MQConstant.NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT, true, false, null);
            // 创建普通队列
            Map<String, Object> queueArgs = new HashMap<>();
            queueArgs.put("x-dead-letter-exchange", MQConstant.DEAD_LETTER_EXCHANGE); // 绑定死信交换机，此处不设置全局TTL，而是每条信息一个TTL
            queueArgs.put("x-dead-letter-routing-key", MQConstant.DEAD_LETTER_ROUTING_KEY);
            channel.queueDeclare(MQConstant.NORMAL_QUEUE, true, false, false, queueArgs);
            // 绑定队列和交换机
            channel.queueBind(MQConstant.NORMAL_QUEUE, MQConstant.NORMAL_EXCHANGE, MQConstant.NORMAL_ROUTING_KEY);

            logger.info("Normal Exchange and Queue Declared Successfully!");

            // 创建死信交换机
            channel.exchangeDeclare(MQConstant.DEAD_LETTER_EXCHANGE, BuiltinExchangeType.DIRECT, true);
            // 创建死信队列
            channel.queueDeclare(MQConstant.DEAD_LETTER_QUEUE, true, false, false, null);
            // 绑定队列和交换机
            channel.queueBind(MQConstant.DEAD_LETTER_QUEUE, MQConstant.DEAD_LETTER_EXCHANGE, MQConstant.DEAD_LETTER_ROUTING_KEY);

            logger.info("Dead Letter Exchange and Queue Declared Successfully!");

        } catch (IOException | TimeoutException e) {
            logger.error("Exchange And Queue Declared Failed!");
            throw new RuntimeException(e);
        }
    }
}
