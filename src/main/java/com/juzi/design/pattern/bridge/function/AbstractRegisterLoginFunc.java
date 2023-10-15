package com.juzi.design.pattern.bridge.function;

import com.juzi.design.pojo.UserInfo;
import com.juzi.design.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author codejuzi
 */
public abstract class AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {

    @Override
    public String login(String account, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String register(UserInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkUserExists(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }


    protected String commonLogin(String account, String password, UserRepository userRepository) {
        UserInfo userInfo = userRepository.findByUsernameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account or password Error!";
        }
        return "Login Success";
    }

    protected String commonRegister(UserInfo userInfo, UserRepository userRepository) {
        if (commonCheckUserExists(userInfo.getUsername(), userRepository)) {
            throw new RuntimeException("User already registered!");
        }

        userInfo.setCreateDate(LocalDateTime.now());
        userRepository.save(userInfo);

        return "Register success!";
    }

    protected boolean commonCheckUserExists(String username, UserRepository userRepository) {
        UserInfo user = userRepository.findByUsername(username);
        return user != null;
    }
}
