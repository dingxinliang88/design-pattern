package com.juzi.design.pattern.bridge.client;

import com.juzi.design.pattern.bridge.abst.AbstractRegisterLoginComponent;
import com.juzi.design.pattern.bridge.abst.factory.RegisterLoginComponentFactory;
import com.juzi.design.pojo.UserInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
@Service
public class UserBridgeService {

    public String login(String account, String password) {
        AbstractRegisterLoginComponent component
                = RegisterLoginComponentFactory.getComponent("Default");
        return component.login(account, password);
    }

    public String register(UserInfo userInfo) {
        AbstractRegisterLoginComponent component
                = RegisterLoginComponentFactory.getComponent("Default");
        return component.register(userInfo);
    }

    public String login3rd(HttpServletRequest request, String type) {
        AbstractRegisterLoginComponent component
                = RegisterLoginComponentFactory.getComponent(type);
        return component.login3rd(request);
    }

}
