package com.yd.manager.entity;

import com.yd.manager.config.hibernate.PhpTimeConvert;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

//访问数 新客数 注册率
@Entity
@Table(name = "estore_user_log")
@Data
public class AccessRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Store store;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(name = "login_time")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime enterTime;

    @Column(name = "out_time")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime leaveTime;

}
