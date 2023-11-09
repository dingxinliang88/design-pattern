package com.juzi.design.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 业务投放实体
 *
 * @author codejuzi
 */
@Data
@Entity
@Table(name = "business_launch")
public class BusinessLaunch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 业务投放详情
     */
    @Column(nullable = false, name = "business_detail")
    private String businessDetail;

    /**
     * 业务投放目的城市
     */
    @Column(nullable = false, name = "target_city")
    private String targetCity;

    /**
     * 业务投放目标性别
     */
    @Column(nullable = false, name = "target_sex")
    private String targetSex;

    /**
     * 业务投放相关产品
     */
    @Column(nullable = false, name = "target_product")
    private String targetProduct;

}
