package com.juzi.design.pattern.bridge.client;

import com.juzi.design.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
@RestController
@RequestMapping("/bridge")
public class UserBridgeController {

    @Autowired
    private UserBridgeService userBridgeService;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userBridgeService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userBridgeService.register(userInfo);
    }

    @GetMapping("/login/gitee")
    public String giteeLogin(HttpServletRequest request) {
        return userBridgeService.login3rd(request, "GITEE");
    }

}
