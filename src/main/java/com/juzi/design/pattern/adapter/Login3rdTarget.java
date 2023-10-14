package com.juzi.design.pattern.adapter;

/**
 * 目标角色，符合依赖倒置原则（面向接口编程）
 *
 * @author codejuzi
 */
public interface Login3rdTarget {

    String loginByGitee(String code, String state);

    String loginByQQ(String... params);
    String loginByWechat(String... params);
}
