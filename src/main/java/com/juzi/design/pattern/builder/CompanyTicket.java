package com.juzi.design.pattern.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product产品类
 *
 * @author codejuzi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTicket implements Cloneable {

    /**
     * 发票固定不变的信息
     */
    private String finalInfo;

    private String title;

    private String taxId;

    private String bankInfo;

    private String product;

    private String content;

    @Override
    protected Object clone() {
        CompanyTicket companyTicket = null;
        try {
            companyTicket = (CompanyTicket) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return companyTicket;
    }
}
