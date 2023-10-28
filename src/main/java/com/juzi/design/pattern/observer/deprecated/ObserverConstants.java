package com.juzi.design.pattern.observer.deprecated;

import java.util.List;
import java.util.Vector;

/**
 * @author codejuzi
 */
@Deprecated
public class ObserverConstants {
    public final static List<AbstractObserver> OBSERVER_LIST = new Vector<>();
    public final static String ORDER_WAIT_PAY = "ORDER_WAIT_PAY";
    public final static String ORDER_WAIT_SEND = "ORDER_WAIT_SEND";
    public final static String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE";
    public final static String ORDER_FINISH = "ORDER_FINISH";
}
