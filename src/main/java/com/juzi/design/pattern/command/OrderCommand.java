package com.juzi.design.pattern.command;

import com.juzi.design.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 具体命令角色
 *
 * @author codejuzi
 */
@Component
public class OrderCommand implements OrderCommandInterface {

    /**
     * 注入命令接收者
     */
    @Autowired
    private OrderCommandReceiver receiver;

    @Override
    public void execute(Order order) {
        // 调用命令接收者的action方法，执行命令
        receiver.action(order);
    }
}
