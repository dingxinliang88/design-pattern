package com.juzi.design.pattern.state.deprecated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codejuzi
 */
@Deprecated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderId;

    private String productId;

    private String state;
}
