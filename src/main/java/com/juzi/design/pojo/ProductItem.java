package com.juzi.design.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author codejuzi
 */
@Data
@Entity
@Table(name = "product_item")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer pid;

}
