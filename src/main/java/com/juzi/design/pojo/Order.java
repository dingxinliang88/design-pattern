package com.juzi.design.pojo;

import com.juzi.design.pattern.state.recommend.OrderState;
import lombok.*;

/**
 * @author codejuzi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String orderId;

    private String productId;

    private OrderState orderState;

    private Float price;

}
