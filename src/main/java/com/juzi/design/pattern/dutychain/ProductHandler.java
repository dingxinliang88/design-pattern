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
public class ProductHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList,
                                               String targetCity, String targetSex, String targetProduct) {
        if (launchList.isEmpty()) {
            return launchList;
        }
        // 过滤
        launchList = launchList.stream().filter(launch -> {
            String product = launch.getTargetProduct();
            if (StrUtil.isEmpty(product)) {
                return true;
            }
            List<String> productList = Arrays.asList(product.split(","));
            return productList.contains(targetProduct);
        }).collect(Collectors.toList());

        // 继续投递
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
