package com.juzi.design.service;

import com.juzi.design.pojo.UserInfo;
import com.juzi.design.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author codejuzi
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUsernameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account or password Error!";
        }
        return "Login Success";
    }

    public String register(UserInfo userInfo) {
        if (checkUserExists(userInfo.getUsername())) {
            throw new RuntimeException("User already registered.");
        }

        userInfo.setCreateDate(LocalDateTime.now());
        userRepository.save(userInfo);

        return "Register success!";
    }

    private boolean checkUserExists(String username) {
        UserInfo user = userRepository.findByUsername(username);
        return user != null;
    }
}
