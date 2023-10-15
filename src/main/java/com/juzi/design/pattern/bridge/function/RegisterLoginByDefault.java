package com.juzi.design.pattern.bridge.function;

import com.juzi.design.pattern.bridge.abst.factory.RegisterLoginComponentFactory;
import com.juzi.design.pojo.UserInfo;
import com.juzi.design.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Bridge - ConcreteImplementor
 *
 * @author codejuzi
 */
@Component
public class RegisterLoginByDefault extends AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initFuncMap() {
        RegisterLoginComponentFactory.FUNC_MAP.put("Default", this);
    }

    @Override
    public String login(String account, String password) {
        return super.commonLogin(account, password, userRepository);
    }

    @Override
    public String register(UserInfo userInfo) {
        return super.commonRegister(userInfo, userRepository);
    }

    @Override
    public boolean checkUserExists(String username) {
        return super.commonCheckUserExists(username, userRepository);
    }

}
