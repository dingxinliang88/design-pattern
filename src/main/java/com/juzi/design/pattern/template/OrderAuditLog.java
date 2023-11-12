package com.juzi.design.pattern.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单审计日志对象
 *
 * @author codejuzi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAuditLog {

    private String account;
    private String action;
    private LocalDateTime date;
    private String orderId;
    private Object details;
}
