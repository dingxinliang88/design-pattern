package com.juzi.design.pattern.template;

import java.time.LocalDateTime;

/**
 * 抽象模板类
 *
 * @author codejuzi
 */
public abstract class AbstractAuditLogProcessor {

    private final OrderAuditLog basicAuditLog(String account, String action, String orderId) {
        OrderAuditLog auditLog = new OrderAuditLog();
        auditLog.setAccount(account);
        auditLog.setAction(action);
        auditLog.setOrderId(orderId);
        auditLog.setDate(LocalDateTime.now());
        return auditLog;
    }

    protected abstract OrderAuditLog buildDetails(OrderAuditLog auditLog);

    public final OrderAuditLog createAuditLog(String account, String action, String orderId) {
        OrderAuditLog auditLog = basicAuditLog(account, action, orderId);
        return buildDetails(auditLog);
    }

}
