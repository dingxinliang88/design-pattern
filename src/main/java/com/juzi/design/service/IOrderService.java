package com.juzi.design.service;

import com.alipay.api.AlipayApiException;
import com.juzi.design.pojo.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
public interface IOrderService {

    Order createOrder(String productId);

    Order pay(String orderId);

    Order send(String orderId);

    Order receive(String orderId);

    String getPayUrl(String orderId, Float price, Integer payType);

    String alipayCallback(HttpServletRequest request) throws AlipayApiException;
}
