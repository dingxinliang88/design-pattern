package com.juzi.design.pattern.dutychain;

import com.juzi.design.pojo.BusinessLaunch;

import java.util.List;

/**
 * 抽象责任类
 *
 * @author codejuzi
 */
public abstract class AbstractBusinessHandler {

    public AbstractBusinessHandler nextHandler;

    public boolean hasNextHandler() {
        return this.nextHandler != null;
    }

    public abstract List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList,
                                                        String targetCity, String targetSex, String targetProduct);
}
