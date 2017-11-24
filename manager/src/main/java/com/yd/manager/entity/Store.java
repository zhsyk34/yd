package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商店
 */
@Entity
@Table(name = "estore_shop")
@Data
public class Store implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "shopname")
    private String name;
    @Column(name = "shopcode")
    private String code;
    @Column(name = "addr")
    private String address;
}
