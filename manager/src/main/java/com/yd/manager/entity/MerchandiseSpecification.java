package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "spec_id")
    private String specId;

    @Column(name = "spec_name")
    private String name;

    private BigDecimal price;

    private int source;
}
