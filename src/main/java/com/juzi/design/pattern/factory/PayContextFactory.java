package com.juzi.design.pattern.factory;

import com.juzi.design.pattern.strategy.PayContext;
import com.juzi.design.pattern.strategy.PayStrategyInterface;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体工厂类
 *
 * @author codejuzi
 */
@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext> {

    /**
     * 享元模式 => 减少重复对象造成的内存浪费情况
     */
    private static final Map<String, PayContext> PAY_CONTEXT_MAP = new ConcurrentHashMap<>();

    @Override
    public PayContext getContext(Integer payType) {
        PayStrategyEnum payStrategyEnum;
        switch (payType) {
            case 1:
                payStrategyEnum = PayStrategyEnum.ALIPAY;
                break;
            case 2:
                payStrategyEnum = PayStrategyEnum.WECHAT_PAY;
                break;
            default:
                throw new UnsupportedOperationException("Pay Type Is Error!");
        }

        // 从map中获取Context
        PayContext payContext = PAY_CONTEXT_MAP.get(payStrategyEnum.name());
        // 第一次调用
        if (payContext == null) {
            // 反射创建，存入Map
            try {
                PayStrategyInterface payStrategy = (PayStrategyInterface) Class.forName(payStrategyEnum.getVal()).newInstance();
                PayContext newPayContext = new PayContext(payStrategy);
                PAY_CONTEXT_MAP.put(payStrategyEnum.name(), newPayContext);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return PAY_CONTEXT_MAP.get(payStrategyEnum.name());
    }
}
