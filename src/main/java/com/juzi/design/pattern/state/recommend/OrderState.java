package com.juzi.design.pattern.state.recommend;

/**
 * 订单状态枚举类
 *
 * @author codejuzi
 */
public enum OrderState {

    ORDER_WAIT_PAY,
    ORDER_WAIT_SEND,
    ORDER_WAIT_RECEIVE,
    ORDER_FINISH;
}
