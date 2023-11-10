package com.juzi.design.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author codejuzi
 */
@Data
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, name = "product_id")
    private String productId;

    @Column(nullable = false, name = "send_red_bag")
    private Integer sendRedBag;

}
