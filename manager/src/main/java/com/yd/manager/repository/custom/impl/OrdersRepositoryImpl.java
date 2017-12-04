package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.OrdersCollectDTO;
import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.Orders;
import com.yd.manager.entity.Orders_;
import com.yd.manager.entity.Store;
import com.yd.manager.entity.Store_;
import com.yd.manager.repository.custom.OrdersDTORepository;
import com.yd.manager.utils.jpa.JpaUtils;
import com.yd.manager.utils.jpa.PredicateFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrdersRepositoryImpl implements OrdersDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<OrdersDTO> listOrdersDTO(TimeRange timeRange, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<OrdersDTO> criteria = builder.createQuery(OrdersDTO.class);

        //from
        Root<Store> storeRoot = criteria.from(Store.class);
        SetJoin<Store, Orders> ordersJoin = storeRoot.join(Store_.orders, JoinType.LEFT);

        //where
        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForOrders(builder, ordersJoin, timeRange))
                .append(this.restrictForStore(storeRoot, stores))
                .get();
        JpaUtils.setPredicate(criteria, predicates);

        //group by
        criteria.groupBy(storeRoot);

        //order by
        Expression<BigDecimal> sum = builder.sum(ordersJoin.get(Orders_.actual));
        criteria.orderBy(builder.desc(sum));

        criteria.multiselect(
                storeRoot.get(Store_.id),
                storeRoot.get(Store_.name),
                storeRoot.get(Store_.address),
                builder.count(ordersJoin),
                sum,
                builder.avg(ordersJoin.get(Orders_.actual))
        );

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);

        //method-3:
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<OrdersDTO> criteria = builder.createQuery(OrdersDTO.class);
//
//        Root<Store> storeRoot = criteria.getOrder(Store.class);
//
//        Subquery<Long> countQuery = criteria.subquery(Long.class);
//        Root<Orders> countRoot = countQuery.getOrder(Orders.class);
//        countQuery.select(builder.count(countRoot));
//        countQuery.where(builder.equal(countRoot.get(Orders_.store), storeRoot));
//
//        Subquery<BigDecimal> sumQuery = criteria.subquery(BigDecimal.class);
//        Root<Orders> sumRoot = sumQuery.getOrder(Orders.class);
//        Expression<BigDecimal> sum = builder.sum(sumRoot.get(Orders_.actual));
//        sumQuery.select(sum);
//        sumQuery.where(builder.equal(sumRoot.get(Orders_.store), storeRoot));
//
//        Subquery<Double> avgQuery = criteria.subquery(Double.class);
//        Root<Orders> avgRoot = avgQuery.getOrder(Orders.class);
//        avgQuery.select(builder.avg(avgRoot.get(Orders_.actual)));
//        avgQuery.where(builder.equal(avgRoot.get(Orders_.store), storeRoot));
//
//        criteria.multiselect(
//                storeRoot.get(Store_.id),
//                storeRoot.get(Store_.name),
//                countQuery.getSelection(),
//                sumQuery.getSelection(),
//                avgQuery.getSelection()
//        );
//
//        if (!CollectionUtils.isEmpty(stores)) {
//            criteria.where(storeRoot.get(Store_.id).in(stores));
//        }
//
//        //TODO:order by sum desc
//        criteria.orderBy(builder.desc(sumQuery.getSelection()));
//
//        TypedQuery<OrdersDTO> query = manager.createQuery(criteria);
//
//        if (pageable != null) {
//            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
//        }
//
//        return query.getResultList();
    }

    @Override
    public OrdersCollectDTO getOrdersCollectDTO(TimeRange timeRange, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<OrdersCollectDTO> criteria = builder.createQuery(OrdersCollectDTO.class);

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

    @SuppressWarnings("Duplicates")
    private Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> path, TimeRange timeRange) {
        if (timeRange == null) {
            return null;
        }

        Collection<Predicate> predicates = new LinkedList<>();
        Optional.ofNullable(timeRange.getBegin()).map(begin -> builder.greaterThanOrEqualTo(path.get(Orders_.createTime), begin)).ifPresent(predicates::add);
        Optional.ofNullable(timeRange.getEnd()).map(end -> builder.lessThanOrEqualTo(path.get(Orders_.createTime), end)).ifPresent(predicates::add);
        return predicates;
    }

}
