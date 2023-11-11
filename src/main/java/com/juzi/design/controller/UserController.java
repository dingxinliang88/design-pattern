package com.juzi.design.controller;

import com.juzi.design.pattern.adapter.Login3rdAdapter;
import com.juzi.design.pojo.BusinessLaunch;
import com.juzi.design.pojo.UserInfo;
import com.juzi.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/business/launch")
    public List<BusinessLaunch> filterBusinessLaunch(@RequestParam("city") String city,
                                                     @RequestParam("sex") String sex,
                                                     @RequestParam("product") String product) {
        return userService.filterBusinessLaunch(city, sex, product);
    }

    @PostMapping("/ticket")
    public Object createTicket(@RequestParam("type") String type,
                               @RequestParam("product_id") String productId,
                               @RequestParam("content") String content,
                               @RequestParam("title") String title,
                               @RequestParam("bank_info") String bankInfo,
                               @RequestParam("tax_id") String taxId) {
        return userService.createTicket(type, productId, content, title, bankInfo, taxId);

    }
}
