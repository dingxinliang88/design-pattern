package com.juzi.design.pattern.mediator;

/**
 * 抽象同事类
 *
 * @author codejuzi
 */
public abstract class AbstractCustomer {

    // 关联中介者对象
    public AbstractMediator mediator;

    public String orderId;

    public String customerName;

    public AbstractCustomer(AbstractMediator mediator, String orderId, String customerName) {
        this.mediator = mediator;
        this.orderId = orderId;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    /**
     * 与中介者进行信息交互的方法，供子类实现
     */
    public abstract void messageTransfer(String orderId, String targetCustomer, String payResult);
}
