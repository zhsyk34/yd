package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品规格
 */
@Entity
@Table(name = "estore_inventory_spec")
@Data
public class MerchandiseSpecification {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private MerchandiseStore MerchandiseStore;

    /**
     * 逗号分隔的规格id字符串
     * table-name:estore_goods_format_groups
     */
    @Column(name = "spec_id")
    private String no;

    @Column(name = "spec_name")
    private String name;

    private BigDecimal price;
}