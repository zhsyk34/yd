package com.yd.manager.entity;

import com.yd.manager.config.hibernate.PhpTimeConvert;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Entity
@Table(name = "estore_order")
@Data
public class Orders {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "dno")
    private String no;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_uid")
    private User user;//null:id=0表示游客
    @Column(name = "buyer_phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Store store;

    @Column(name = "allcost")
    private BigDecimal original;//订单原始金额
    @Column(name = "sale")
    private BigDecimal remission;//优惠金额
    @Column(name = "paid_cost")
    private BigDecimal actual;//实际金额

    private BigDecimal profit;//利润

    @Column(name = "paystatus")
    private boolean paid;//是否付款

    /**
     * 付款方式
     * 1:微信,2:支付宝,3:余额支付
     */
    @Column(name = "paytype")
    private String type;

    /**
     * 订单状态
     * 0:待接单,1:准备中,2:待取货,3:退款中,4:已完成,5:退单完成,6:已取消
     */
    private String status;

    @Column(name = "addtime")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime createTime;//下单时间
    @Column(name = "paytime")
    @Convert(converter = PhpTimeConvert.class)
    private LocalDateTime payTime;//支付时间

}
