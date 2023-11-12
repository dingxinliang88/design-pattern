package com.juzi.design.pattern.mediator;

/**
 * 具体同事类
 *
 * @author codejuzi
 */
public class Buyer extends AbstractCustomer {

    public Buyer(AbstractMediator mediator, String orderId, String customerName) {
        super(mediator, orderId, customerName);
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, String payResult) {
        super.mediator.messageTransfer(orderId, targetCustomer, this, payResult);
    }
}
