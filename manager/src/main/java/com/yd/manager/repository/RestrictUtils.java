package com.yd.manager.repository;

import com.yd.manager.entity.Orders;
import com.yd.manager.entity.Orders_;
import com.yd.manager.util.jpa.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

public abstract class RestrictUtils {

    //订单过滤
    public static Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> ordersPath) {
        return PredicateBuilder.instance()
                .append(builder.equal(ordersPath.get(Orders_.status), 4))//已完成
                .append(builder.equal(ordersPath.get(Orders_.type), 1))//云店
                .build();
    }
}
