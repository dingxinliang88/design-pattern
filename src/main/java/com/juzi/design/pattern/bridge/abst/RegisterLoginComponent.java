package com.juzi.design.pattern.bridge.abst;

import com.juzi.design.pattern.bridge.function.RegisterLoginFuncInterface;
import com.juzi.design.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Bridge - RefinedAbstraction
 *
 * @author codejuzi
 */
public class RegisterLoginComponent extends AbstractRegisterLoginComponent {

    /**
     * 构造器传入桥梁
     *
     * @param funcInterface Bridge
     */
    public RegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        super(funcInterface);
    }

    @Override
    public String login(String account, String password) {
        return funcInterface.login(account, password);
    }

    @Override
    public String register(UserInfo userInfo) {
        return funcInterface.register(userInfo);
    }

//    @Override
//    public boolean checkUserExists(String username) {
//        return funcInterface.checkUserExists(username);
//    }

    @Override
    public String login3rd(HttpServletRequest request) {
        return funcInterface.login3rd(request);
    }
}
