package com.juzi.design.pattern.builder;

import org.springframework.stereotype.Component;

/**
 * 具体指挥者类
 * （代理模式）RealSubject 具体主题角色
 *
 * @author codejuzi
 */
@Component
public class Director extends AbstractDirector {

    @Override
    public Object buildTicket(String type, String product, String content, String title, String bankInfo, String taxId) {
        switch (type) {
            case "personal":
                PersonalTicketBuilder personalTicketBuilder = new PersonalTicketBuilder();
                personalTicketBuilder.setCommonInfo(title, product, content);
                return personalTicketBuilder.buildTicket();
            case "company":
                CompanyTicketBuilder companyTicketBuilder = new CompanyTicketBuilder();
                companyTicketBuilder.setCommonInfo(title, product, content);
                companyTicketBuilder.setTaxId(taxId);
                companyTicketBuilder.setBankInfo(bankInfo);
                return companyTicketBuilder.buildTicket();
            default:
                throw new UnsupportedOperationException("Ticket Type is Invalid!");
        }
    }
}
