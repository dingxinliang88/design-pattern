package com.juzi.design.pattern.factory;

/**
 * 策略枚举类（策略工厂模式）
 *
 * @author codejuzi
 */
public enum PayStrategyEnum {

    ALIPAY("com.juzi.design.pattern.strategy.AliPayStrategy"),
    WECHAT_PAY("com.juzi.design.pattern.strategy.WechatPayStrategy");

    /**
     * 策略类全类名
     */
    final String val;

    PayStrategyEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
