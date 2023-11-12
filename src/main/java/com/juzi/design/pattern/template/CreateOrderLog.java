package com.juzi.design.pattern.template;

import org.springframework.stereotype.Component;

/**
 * 实现模板方法类子类
 *
 * @author codejuzi
 */
@Component
public class CreateOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        return auditLog;
    }
}
