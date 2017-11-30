package com.yd.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    private Store store;

    private LocalDateTime visitTime;
}
