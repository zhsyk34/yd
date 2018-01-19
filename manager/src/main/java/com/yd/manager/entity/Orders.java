package com.yd.manager.entity;

import com.yd.manager.config.hibernate.PhpTimeConvert;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "estore_order")
@Data
public class Orders {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "dno")
    private String no;

    @ManyToOne
    @JoinColumn(name = "buyer_uid")
    private User user;
    @Column(name = "buyer_phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Store store;

    @Column(name = "allcost")
    private BigDecimal original;
    @Column(name = "sale")
    private BigDecimal remission;
    @Column(name = "paid_cost")
    private BigDecimal actual;

    private BigDecimal profit;

    @Column(name = "paystatus")
    private boolean paid;

    @Column(name = "paytype")
    private String payType;

    private String status;

    @Column(name = "order_type")
    private int type;

    @Column(name = "addtime")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime createTime;
    @Column(name = "paytime")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime payTime;

}
