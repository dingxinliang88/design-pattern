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
@Deprecated
public class OrderV1 {

    private String orderId;

    private String productId;

    private String state;
}
