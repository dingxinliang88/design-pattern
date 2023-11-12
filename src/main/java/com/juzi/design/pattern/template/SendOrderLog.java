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
public class SendOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        // 增加快递公司信息和快递编号
        Map<String, String> extraLog = new HashMap<>();
        extraLog.put("快递公司",  "东风快递");
        extraLog.put("快递编号", "1001001001");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
