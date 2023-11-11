package com.juzi.design.service;

import com.juzi.design.pattern.dutychain.AbstractBusinessHandler;
import com.juzi.design.pattern.dutychain.CityHandler;
import com.juzi.design.pattern.factory.ChainHandlerEnum;
import com.juzi.design.pattern.proxy.DirectorProxy;
import com.juzi.design.pojo.BusinessLaunch;
import com.juzi.design.pojo.UserInfo;
import com.juzi.design.repo.BusinessLaunchRepository;
import com.juzi.design.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author codejuzi
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessLaunchRepository businessLaunchRepository;

    @Autowired
    private DirectorProxy directorProxy;

    @Value("${duty.chain:city,sex,product}")
    private String handlerType;

    // 当前责任链头节点配置
    private String currHandlerType;
    private AbstractBusinessHandler currHandler;

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

    public List<BusinessLaunch> filterBusinessLaunch(String city, String sex, String product) {
        List<BusinessLaunch> launchList = businessLaunchRepository.findAll();
        AbstractBusinessHandler handler = buildHandlerChain();
        if (handler == null) {
            return new ArrayList<>();
        }
        return handler.processHandler(launchList, city, sex, product);
    }

    protected boolean checkUserExists(String username) {
        UserInfo user = userRepository.findByUsername(username);
        return user != null;
    }

    // Handler Chain: CityHandler -> SexHandler -> ProductHandler
    private AbstractBusinessHandler buildHandlerChain() {
        if (handlerType == null) {
            return null;
        }

        if (currHandlerType == null) {
            this.currHandlerType = this.handlerType;
        }


        // duty.chain未改变 && currHandler已经初始化过，直接返回
        if (handlerType.equals(currHandlerType) && currHandler != null) {
            return currHandler;
        }
        // duty.chain改变，或者需要初始化currHandler
        logger.info("Chain Handler Configs Update, Init Handler ......");
        synchronized (this) {
            try {
                // 哑结点
                AbstractBusinessHandler dummyHeadHandler = new CityHandler();
                AbstractBusinessHandler preHandler = dummyHeadHandler;
                String[] handlerTypeList = handlerType.split(",");
                for (String handlerType : handlerTypeList) {
                    AbstractBusinessHandler handler =
                            (AbstractBusinessHandler) Class.forName(ChainHandlerEnum.valueOf(handlerType).getValue())
                                    .newInstance();
                    preHandler.nextHandler = handler;
                    preHandler = handler;
                }
                // 更新值
                this.currHandler = dummyHeadHandler.nextHandler;
                this.currHandlerType = this.handlerType;
                return currHandler;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        return directorProxy.buildTicket(type, productId, content, title, bankInfo, taxId);
    }
}
