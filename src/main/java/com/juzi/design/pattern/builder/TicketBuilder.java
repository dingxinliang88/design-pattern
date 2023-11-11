package com.juzi.design.pattern.builder;

/**
 * 抽象建造者Builder
 *
 * @author codejuzi
 */
public abstract class TicketBuilder<T> {

    /**
     * 设置通用信息
     *
     * @param title   标题
     * @param product 产品信息
     * @param content 内容
     */
    public abstract void setCommonInfo(String title, String product, String content);

    public void setTaxId(String taxId) {
        throw new UnsupportedOperationException();
    }

    public void setBankInfo(String bankInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * 抽象建造方法
     *
     * @return Ticket Object
     */
    public abstract T buildTicket();

}
