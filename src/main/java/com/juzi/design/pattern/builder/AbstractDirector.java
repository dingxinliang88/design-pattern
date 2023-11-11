package com.juzi.design.pattern.builder;

/**
 * 抽象指挥者类
 * （代理模式）Subject 抽象主角角色
 *
 * @author codejuzi
 */
public abstract class AbstractDirector {

    /**
     * @param type company || personal
     */
    public abstract Object buildTicket(String type, String product, String content, String title, String bankInfo, String taxId);
}
