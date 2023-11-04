package com.juzi.design.pattern.strategy;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.juzi.design.pojo.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 支付宝支付策略类
 *
 * @author codejuzi
 */
public class AliPayStrategy implements PayStrategyInterface {

    private static String appId;
    private static String appPrivateKey;
    private static String alipayPublicKey;
    private static String gatewayUrl;
    private static String callbackUrl;
    private static String signType;

    static {
        Properties props = new Properties();
        InputStream input = null;

        try {
            input = AliPayStrategy.class.getResourceAsStream("/conf/pay.properties");
            // load a properties file
            props.load(input);

            appId = props.getProperty("third_party.alipay.app_id");
            appPrivateKey = props.getProperty("third_party.alipay.app_private_key");
            alipayPublicKey = props.getProperty("third_party.alipay.alipay_public_key");
            gatewayUrl = props.getProperty("third_party.alipay.alipay_gateway_url");
            callbackUrl = props.getProperty("third_party.alipay.callback_url");
            signType = props.getProperty("third_party.alipay.sign_type");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


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
            return alipayClient.pageExecute(payRequest, "GET").getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException("AliPay Failed!", e);
        }
    }
}
