package com.juzi.design.pattern.dutychain;

import cn.hutool.core.util.StrUtil;
import com.juzi.design.pojo.BusinessLaunch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 具体责任类
 *
 * @author codejuzi
 */
public class CityHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList,
                                               String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        // 过滤
        launchList = launchList.stream().filter(launch -> {
            String city = launch.getTargetCity();
            if (StrUtil.isEmpty(city)) {
                return true;
            }
            List<String> cityList = Arrays.asList(city.split(","));
            return cityList.contains(targetCity);
        }).collect(Collectors.toList());

        // 继续投递
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
