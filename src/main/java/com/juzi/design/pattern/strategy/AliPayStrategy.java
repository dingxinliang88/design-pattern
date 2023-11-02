package com.juzi.design.pattern.strategy;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.juzi.design.pojo.Order;
import org.springframework.beans.factory.annotation.Value;

/**
 * 支付宝支付策略类
 *
 * @author codejuzi
 */
public class AliPayStrategy implements PayStrategyInterface {
    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.app-private-key}")
    private String appPrivateKey;

    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;

    @Value("${alipay.gateway-url}")
    private String gatewayUrl;

    @Value("${alipay.callback-url")
    private String callbackUrl;

    @Value("${alipay.sign-type}")
    private String signType;


    @Override
    public String pay(Order order) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, appPrivateKey, "JSON", "UTF-8", alipayPublicKey, signType);
        // 设置请求参数
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(callbackUrl);
        payRequest.setBizContent("{\n" +
                "  \"out_trade_no\": \"" + order.getOrderId() + "\",\n" +
                "  \"total_amount\": " + order.getPrice() + ",\n" +
                "  \"subject\": \"CodeJuzi\",\n" +
                "  \"body\": \"商品描述\",\n" +
                "  \"product_code\": \"FAST_INSTANT_TRADE_PAY\"\n" +
                "}");

        // 请求
        try {
            return alipayClient.pageExecute(payRequest).getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException("AliPay Failed!", e);
        }
    }
}
