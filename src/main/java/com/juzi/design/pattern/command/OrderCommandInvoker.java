package com.juzi.design.pattern.command;

import com.juzi.design.pojo.Order;

/**
 * 命令调用者
 *
 * @author codejuzi
 */
public class OrderCommandInvoker {

    public void invoke(OrderCommandInterface command, Order order) {
        // 调用命令
        command.execute(order);
    }
}
