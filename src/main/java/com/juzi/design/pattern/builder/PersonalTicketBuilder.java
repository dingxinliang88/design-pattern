package com.juzi.design.pattern.builder;

/**
 * 具体建造者 Concrete Builder
 *
 * @author codejuzi
 */
public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket> {

    private final PersonalTicket personalTicket = new PersonalTicket();

    @Override
    public void setCommonInfo(String title, String product, String content) {
        personalTicket.setTitle(title);
        personalTicket.setProduct(product);
        personalTicket.setContent(content);
    }

    @Override
    public PersonalTicket buildTicket() {
        return personalTicket;
    }
}
