package com.juzi.design.pattern.composite;

/**
 * @author codejuzi
 */
public abstract class AbstractProductItem {

    protected void addProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child add!");
    }

    protected void delProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child delete!");
    }
}
