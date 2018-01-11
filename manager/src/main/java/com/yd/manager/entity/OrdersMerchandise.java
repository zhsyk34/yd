package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

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
    private MerchandiseStore merchandiseStore;

    @Column(name = "spec_id")
    private String specId;

    @Column(name = "qty")
    private int count;
}
