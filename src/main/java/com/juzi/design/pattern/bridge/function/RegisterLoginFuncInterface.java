package com.juzi.design.pattern.bridge.function;

import com.juzi.design.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Bridge - Implementor
 *
 * @author codejuzi
 */
public interface RegisterLoginFuncInterface {

    String login(String account, String password);

    String register(UserInfo userInfo);

    boolean checkUserExists(String username);

    String login3rd(HttpServletRequest request);
}
