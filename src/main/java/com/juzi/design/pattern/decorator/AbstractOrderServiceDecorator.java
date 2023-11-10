package com.juzi.design.pattern.decorator;

import com.alipay.api.AlipayApiException;
import com.juzi.design.pojo.Order;
import com.juzi.design.service.IOrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象装饰器
 *
 * @author codejuzi
 */
public abstract class AbstractOrderServiceDecorator implements IOrderService {

    private IOrderService orderService;

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Order createOrder(String productId) {
        return orderService.createOrder(productId);
    }

    @Override
    public Order pay(String orderId) {
        return orderService.pay(orderId);
    }

    @Override
    public Order send(String orderId) {
        return orderService.send(orderId);
    }

    @Override
    public Order receive(String orderId) {
        return orderService.receive(orderId);
    }

    @Override
    public String getPayUrl(String orderId, Float price, Integer payType) {
        return orderService.getPayUrl(orderId, price, payType);
    }

    @Override
    public String alipayCallback(HttpServletRequest request) throws AlipayApiException {
        return orderService.alipayCallback(request);
    }

    protected abstract void updateScoreAndSendRedPaper(String productId, Integer serviceLevel, Float price);
}
