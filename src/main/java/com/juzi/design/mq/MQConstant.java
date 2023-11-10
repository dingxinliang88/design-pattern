package com.juzi.design.mq;

/**
 * @author codejuzi
 */
public class MQConstant {

    // direct 类型 => 普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String NORMAL_ROUTING_KEY = "normal_routing_key";

    // fanout 类型 => 死信队列
    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead_letter_routing_key";

}
