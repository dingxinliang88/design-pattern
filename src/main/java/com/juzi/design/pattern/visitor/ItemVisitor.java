package com.juzi.design.pattern.visitor;

import com.juzi.design.pattern.composite.AbstractProductItem;

/**
 * Visitor抽象访问者
 *
 * @author codejuzi
 */
public interface ItemVisitor<T> {

    T visitor(AbstractProductItem productItem);

}
