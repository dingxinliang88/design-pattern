package com.juzi.design.pattern.factory;

/**
 * 责任链Builder Enum
 *
 * @author codejuzi
 */
public enum ChainHandlerEnum {
    city("com.juzi.design.pattern.dutychain.CityHandler"),
    sex("com.juzi.design.pattern.dutychain.SexHandler"),
    product("com.juzi.design.pattern.dutychain.ProductHandler");

    final String value;

    ChainHandlerEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
