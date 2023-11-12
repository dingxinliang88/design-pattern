package com.juzi.design.pattern.mediator;

/**
 * 抽象中介者类
 *
 * @author codejuzi
 */
public abstract class AbstractMediator {

    /**
     * @param orderId        用户的订单ID
     * @param targetCustomer 目标用户，比如张三找李四帮忙代付，那么李四就是目标用户
     * @param customer       抽象同事类，中介者需要根据该参数确定调用者的角色（购买者或者帮忙支付者）
     * @param payResult      支付结果，只有在支付成功之后此参数才不为null
     */
    public abstract void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult);
}
