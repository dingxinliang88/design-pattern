package com.juzi.design.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author codejuzi
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column
    private String userEmail;
}
