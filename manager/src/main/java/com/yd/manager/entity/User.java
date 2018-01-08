package com.yd.manager.entity;

import com.yd.manager.config.hibernate.PhpTimeConvert;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "estore_user")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "user_nickname")
    private String name;
    @Column(name = "mobile")
    private String phone;

    @Column(name = "user_address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "shop_code", referencedColumnName = "shopcode")
    private Store store;

    @Column(name = "user_money")
    private BigDecimal balance;

    @Column(name = "create_time")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "user")
    private Set<Orders> orders;
}
