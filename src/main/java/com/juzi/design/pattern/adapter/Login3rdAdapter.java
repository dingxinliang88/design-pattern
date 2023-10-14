package com.juzi.design.pattern.adapter;

import cn.hutool.json.JSONObject;
import com.juzi.design.pojo.UserInfo;
import com.juzi.design.service.UserService;
import com.juzi.design.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 第三方登录适配器类
 * 通过继承 被适配角色 UserSer
 *
 * @author codejuzi
 */
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget {

    @Value("${gitee.state}")
    private String giteeState;
    /**
     * 填充code
     */
    @Value("${gitee.token-url}")
    private String giteeTokenUrlPattern;

    /**
     * 填充token
     */
    @Value("${gitee.user.url}")
    private String giteeUserUrlPattern;

    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;

    /**
     * <a href="https://gitee.com/oauth/authorize?client_id=%s&redirect_uri=http://localhost:8081/login/gitee&response_type=code&state=GITEE">get code</a>
     */
    @Override
    public String loginByGitee(String code, String state) {
        // 判断state
        if (!giteeState.equals(state)) {
            throw new RuntimeException("Invalid state");
        }

        // token url
        String tokenUrl = String.format(giteeTokenUrlPattern, code);
        JSONObject tokenResp = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        String token = String.valueOf(tokenResp.get("access_token"));

        // user url
        String userUrl = String.format(giteeUserUrlPattern, token);
        JSONObject userInfoResp = HttpClientUtils.execute(userUrl, HttpMethod.GET);

        // 解析用户信息，获取用户名
        String username = giteeUserPrefix + userInfoResp.get("name");

        return autoRegister3rdAndLogin(username, username);
    }

    private String autoRegister3rdAndLogin(String username, String password) {
        // 如果已经使用第三方登录过，直接登录
        if (super.checkUserExists(username)) {
            return super.login(username, password);
        }

        // 组装用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(LocalDateTime.now());

        // 如果是第一次使用第三方登录，先注册
        super.register(userInfo);
        // 再登录
        return super.login(username, password);
    }

    @Override
    public String loginByQQ(String... params) {
        return null;
    }

    @Override
    public String loginByWechat(String... params) {
        return null;
    }
}
