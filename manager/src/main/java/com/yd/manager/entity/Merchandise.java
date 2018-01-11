package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "estore_goods")
@Data
public class Merchandise {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(name = "goodscode")
    private String code;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MerchandiseCategory category;
}
