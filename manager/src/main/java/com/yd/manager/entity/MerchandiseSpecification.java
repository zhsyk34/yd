package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品规格:库存+规格(具体商品)
 */
@Entity
@Table(name = "estore_inventory_spec")
@Data
public class MerchandiseSpecification {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 商品库存
     */
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private MerchandiseStore MerchandiseStore;

    /**
     * 商品规格
     * 逗号分隔的规格id字符串
     * table-name.spec_id:estore_goods_format_groups
     */
    @Column(name = "spec_id")
    private String specId;

    @Column(name = "spec_name")
    private String name;

    private BigDecimal price;
}
