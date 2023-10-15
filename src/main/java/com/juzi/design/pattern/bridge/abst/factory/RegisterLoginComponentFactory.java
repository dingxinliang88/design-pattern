package com.juzi.design.pattern.bridge.abst.factory;

import com.juzi.design.pattern.bridge.abst.AbstractRegisterLoginComponent;
import com.juzi.design.pattern.bridge.abst.RegisterLoginComponent;
import com.juzi.design.pattern.bridge.function.RegisterLoginFuncInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author codejuzi
 */
public class RegisterLoginComponentFactory {

    public static final Map<String, AbstractRegisterLoginComponent> COMPONENT_MAP = new ConcurrentHashMap<>();

    public static final Map<String, RegisterLoginFuncInterface> FUNC_MAP = new ConcurrentHashMap<>();

    public static AbstractRegisterLoginComponent getComponent(String type) {
        AbstractRegisterLoginComponent component = COMPONENT_MAP.get(type);
        if(component == null) {
            synchronized (COMPONENT_MAP) {
                component = COMPONENT_MAP.get(type);
                if(component == null) {
                    component = new RegisterLoginComponent(FUNC_MAP.get(type));
                    COMPONENT_MAP.put(type, component);
                }
            }
        }
        return component;
    }
}
