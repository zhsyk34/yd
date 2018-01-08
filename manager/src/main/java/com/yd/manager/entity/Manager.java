package com.yd.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yd.manager.config.hibernate.PhpTimeConvert;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "estore_admin")
@Data
public class Manager {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_login")
    private String loginName;
    @Column(name = "user_pass")
    private String password;

    @Column(name = "user_truename")
    private String name;
    @Column(name = "mobile")
    private String phone;
    @Column(name = "user_address")
    private String address;

    /**
     * 用户类型
     * 0:超级管理员,1一般管理员,2:代理商,3:小蜜蜂,4:店主
     */
    @Column(name = "user_type")
    private int type;

    @Column(name = "shop_id")
    @JsonIgnore
    private String stores;//php-serialize

    @Column(name = "user_money")
    private BigDecimal balance;

    @Column(name = "create_time")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime createTime;
}
