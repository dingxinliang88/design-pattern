package com.juzi.design.pattern.builder;

/**
 * 具体建造者 Concrete Builder
 *
 * @author codejuzi
 */
public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {

    private final CompanyTicket companyTicket = new CompanyTicket();
    @Override
    public void setCommonInfo(String title, String product, String content) {
        companyTicket.setTitle(title);
        companyTicket.setProduct(product);
        companyTicket.setContent(content);
    }

    @Override
    public void setTaxId(String taxId) {
        companyTicket.setTaxId(taxId);
    }


    @Override
    public void setBankInfo(String bankInfo) {
        companyTicket.setBankInfo(bankInfo);
    }

    @Override
    public CompanyTicket buildTicket() {
        return companyTicket;
    }
}
