package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.Orders;
import com.yd.manager.entity.Orders_;
import com.yd.manager.entity.Store;
import com.yd.manager.entity.Store_;
import com.yd.manager.repository.custom.OrdersDTORepository;
import com.yd.manager.utils.jpa.JpaUtils;
import com.yd.manager.utils.jpa.PredicateFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@Repository
public class OrdersRepositoryImpl implements OrdersDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public OrdersDTO getOrdersDTO(TimeRange timeRange, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<OrdersDTO> criteria = builder.createQuery(OrdersDTO.class);

        Root<Orders> ordersRoot = criteria.from(Orders.class);
        Join<Orders, Store> storeJoin = ordersRoot.join(Orders_.store);

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForOrders(builder, ordersRoot, timeRange))
                .append(this.restrictForStore(storeJoin, stores))
                .get();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.multiselect(
                builder.count(ordersRoot),
                builder.sum(ordersRoot.get(Orders_.actual)),
                builder.avg(ordersRoot.get(Orders_.actual))
        );

        return manager.createQuery(criteria).getSingleResult();
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

    private Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(Orders_.createTime), timeRange);
    }

}
