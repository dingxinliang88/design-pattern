package com.juzi.design.pattern.template;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现模板方法类子类
 *
 * @author codejuzi
 */
@Component
public class PayOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        // 增加支付类型和实际支付金额
        Map<String, String> extraLog = new HashMap<>();
        extraLog.put("pay-type", "AliPay");
        extraLog.put("price", "100");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
