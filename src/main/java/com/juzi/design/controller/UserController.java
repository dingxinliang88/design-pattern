package com.juzi.design.controller;

import com.juzi.design.pattern.adapter.Login3rdAdapter;
import com.juzi.design.pojo.UserInfo;
import com.juzi.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Login3rdAdapter login3rdAdapter;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userService.register(userInfo);
    }

    @GetMapping("/login/gitee")
    public String giteeLogin(String code, String state) {
        return login3rdAdapter.loginByGitee(code, state);
    }
}
