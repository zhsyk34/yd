package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品库存
 */
@Entity
@Table(name = "estore_inventory_shop")
@Data
public class MerchandiseStore {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Merchandise merchandise;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Store store;
}
