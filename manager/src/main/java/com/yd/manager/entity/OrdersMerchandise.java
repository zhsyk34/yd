package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 订单商品
 */
@Entity
@Table(name = "estore_order_goods")
@Data
public class OrdersMerchandise {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private MerchandiseSpecification specification;

    @Column(name = "qty")
    private int count;
}
