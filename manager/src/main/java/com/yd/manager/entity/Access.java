package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;

//TODO
@Entity
@Table(name = "estore_user_action_log")
@Data
public class Access {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private int count;

    //TODO
    @Transient
    private String shopCode;

//    @Column(name = "last_visit_time")
//    private LocalDateTime last;//最后访问时间
}
