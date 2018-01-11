package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estore_goods_category")
@Data
public class MerchandiseCategory {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
