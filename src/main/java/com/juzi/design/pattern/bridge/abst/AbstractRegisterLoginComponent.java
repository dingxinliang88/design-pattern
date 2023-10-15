package com.juzi.design.pattern.bridge.abst;

import com.juzi.design.pattern.bridge.function.RegisterLoginFuncInterface;
import com.juzi.design.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Bridge - Abstraction
 *
 * @author codejuzi
 */
public abstract class AbstractRegisterLoginComponent {

    /**
     * Bridge
     */
    protected RegisterLoginFuncInterface funcInterface;

    public AbstractRegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
//        validate(funcInterface);
        this.funcInterface = funcInterface;
    }

//    protected final void validate(RegisterLoginFuncInterface funcInterface) {
//        if (!(funcInterface instanceof RegisterLoginFuncInterface)) {
//            throw new UnsupportedOperationException("Unknown register / login function Type!");
//        }
//    }

    public abstract String login(String account, String password);

    public abstract String register(UserInfo userInfo);

//    public abstract boolean checkUserExists(String username);

    public abstract String login3rd(HttpServletRequest request);
}
