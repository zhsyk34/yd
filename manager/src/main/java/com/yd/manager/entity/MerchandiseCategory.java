package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品种类
 */
@Entity
@Table(name = "estore_goods_category")
@Data
public class MerchandiseCategory {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
