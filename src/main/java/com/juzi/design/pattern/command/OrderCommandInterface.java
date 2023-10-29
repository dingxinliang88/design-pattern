package com.juzi.design.pattern.command;

import com.juzi.design.pojo.Order;

/**
 * 抽象命令角色
 *
 * @author codejuzi
 */
public interface OrderCommandInterface {

    /**
     * 执行命令
     */
    void execute(Order order);
}
