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
public class PersonalTicket implements Cloneable {

    /**
     * 发票固定不变的信息
     */
    private String finalInfo;

    private String title;

    private String product;

    private String content;

    @Override
    protected Object clone() {
        PersonalTicket personalTicket = null;
        try {
            personalTicket = (PersonalTicket) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return personalTicket;
    }
}
