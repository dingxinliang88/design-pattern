package com.juzi.design.pattern.proxy;

import com.juzi.design.pattern.builder.AbstractDirector;
import com.juzi.design.pattern.builder.Director;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Proxy 代理对象
 *
 * @author codejuzi
 */
@Component
public class DirectorProxy extends AbstractDirector {

    private final Logger logger = LoggerFactory.getLogger(DirectorProxy.class);

    @Autowired
    private Director director;

    @Override
    public Object buildTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        // previous process
        String product = getProduct(productId);
        if (bankInfo == null || !validateBankInfo(bankInfo)) {
            return null;
        }
        // 代理 director的 buildTicket方法
        return director.buildTicket(type, product, content, title, bankInfo, taxId);
    }

    private String getProduct(String productId) {
        return "Get product by product id: " + productId + ". In the proxy object, previous process";
    }


    private boolean validateBankInfo(String backInfo) {
        logger.info("Validate bank info, in proxy object");
        return true;
    }
}
