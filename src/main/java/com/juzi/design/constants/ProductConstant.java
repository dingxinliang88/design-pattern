package com.juzi.design.constants;

/**
 * @author codejuzi
 */
public interface ProductConstant {

    // region redis key
    String PRODUCT_KEY_PREFIX = "product:";

    String PRODUCT_CACHE_KEY = PRODUCT_KEY_PREFIX + "items";

    // endregion
}
