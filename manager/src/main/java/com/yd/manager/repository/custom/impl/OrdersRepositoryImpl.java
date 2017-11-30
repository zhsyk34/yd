package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.*;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.OrdersDTORepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yd.manager.utils.jpa.JpaUtils.getOrderFromSort;

@Repository
public class OrdersRepositoryImpl implements OrdersDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserOrdersDTO> findUserOrderDTO(Long userId, LocalDateTime begin, LocalDateTime end, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserOrdersDTO> criteria = builder.createQuery(UserOrdersDTO.class);

        Root<Orders> ordersRoot = criteria.from(Orders.class);
        Join<Orders, User> userJoin = ordersRoot.join(Orders_.user);
        Join<Orders, Store> storeJoin = ordersRoot.join(Orders_.store);

        criteria.multiselect(
                userJoin.get(User_.id),
                userJoin.get(User_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                ordersRoot.get(Orders_.id),
                ordersRoot.get(Orders_.actual)
        );

        List<Predicate> predicates = this.predicates(builder, ordersRoot, userJoin, storeJoin, userId, begin, end, stores);

        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        if (pageable != null) {
            criteria.orderBy(getOrderFromSort(builder, ordersRoot, pageable.getSort()));
        }

        TypedQuery<UserOrdersDTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    @Override
    public List<OrdersDTO> findOrdersCollectDTO(LocalDateTime begin, LocalDateTime end, List<Long> stores, Pageable pageable) {
        //method-1

//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<OrdersDTO> criteria = builder.createQuery(OrdersDTO.class);
//
//        Root<Orders> ordersRoot = criteria.getOrder(Orders.class);
//        Join<Orders, Store> storeJoin = ordersRoot.join(Orders_.store);
//
//        Expression<BigDecimal> sum = builder.sum(ordersRoot.get(Orders_.actual));
//        criteria.multiselect(
//                storeJoin.get(Store_.id),
//                storeJoin.get(Store_.name),
//                builder.count(ordersRoot),
//                sum,
//                builder.avg(ordersRoot.get(Orders_.actual))
//        );
//
//        criteria.groupBy(storeJoin.get(Store_.id), storeJoin.get(Store_.name));
//
//        List<Predicate> predicates = predicates(builder, ordersRoot, storeJoin, begin, end, stores);
//
//        if (!CollectionUtils.isEmpty(predicates)) {
//            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
//        }
//
//        //order by sum desc
//        criteria.orderBy(builder.desc(sum));
//
//        TypedQuery<OrdersDTO> query = manager.createQuery(criteria);
//
//        if (pageable != null) {
//            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
//        }
//
//        return query.getResultList();

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<OrdersDTO> criteria = builder.createQuery(OrdersDTO.class);

        Root<Store> storeRoot = criteria.from(Store.class);
        SetJoin<Store, Orders> ordersJoin = storeRoot.join(Store_.orders, JoinType.LEFT);

        List<Predicate> predicates = this.predicates(builder, ordersJoin, null, begin, end, null);
        if (!CollectionUtils.isEmpty(predicates)) {
            ordersJoin.on(predicates.toArray(new Predicate[predicates.size()]));
        }

        if (!CollectionUtils.isEmpty(stores)) {
            criteria.where(storeRoot.get(Store_.id).in(stores));
        }

        Expression<BigDecimal> sum = builder.sum(ordersJoin.get(Orders_.actual));
        criteria.multiselect(
                storeRoot.get(Store_.id),
                storeRoot.get(Store_.name),
                builder.count(ordersJoin),
                sum,
                builder.avg(ordersJoin.get(Orders_.actual))
        );

        criteria.groupBy(storeRoot);

        //order by sum desc
        criteria.orderBy(builder.desc(sum));

        TypedQuery<OrdersDTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();

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
    public Orders2DTO findOrdersCollectDTO2(LocalDateTime begin, LocalDateTime end, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Orders2DTO> criteria = builder.createQuery(Orders2DTO.class);

        Root<Orders> ordersRoot = criteria.from(Orders.class);
        Join<Orders, Store> storeJoin = ordersRoot.join(Orders_.store);

        Expression<BigDecimal> sum = builder.sum(ordersRoot.get(Orders_.actual));
        criteria.multiselect(
                builder.count(ordersRoot),
                sum,
                builder.avg(ordersRoot.get(Orders_.actual))
        );

        List<Predicate> predicates = this.predicates(builder, ordersRoot, storeJoin, begin, end, stores);

        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        return manager.createQuery(criteria).getSingleResult();
    }

    private List<Predicate> predicates(CriteriaBuilder builder, Path<Orders> ordersRoot, From<?, Store> storeJoin, LocalDateTime begin, LocalDateTime end, List<Long> stores) {
        List<Predicate> predicates = new ArrayList<>();

        Path<LocalDateTime> createTime = ordersRoot.get(Orders_.createTime);
        if (begin != null) {
            predicates.add(builder.greaterThanOrEqualTo(createTime, begin));
        }
        if (end != null) {
            predicates.add(builder.lessThanOrEqualTo(createTime, end));
        }

        if (!CollectionUtils.isEmpty(stores)) {
            predicates.add(storeJoin.get(Store_.id).in(stores));
        }

        return predicates;
    }

    private List<Predicate> predicates(CriteriaBuilder builder, Path<Orders> ordersRoot, From<?, User> userJoin, Join<?, Store> storeJoin, Long userId, LocalDateTime begin, LocalDateTime end, List<Long> stores) {
        List<Predicate> predicates = new ArrayList<>();
        if (userId != null) {
            predicates.add(builder.equal(userJoin.get(User_.id), userId));
        }

        Path<LocalDateTime> createTime = ordersRoot.get(Orders_.createTime);
        if (begin != null) {
            predicates.add(builder.greaterThanOrEqualTo(createTime, begin));
        }
        if (end != null) {
            predicates.add(builder.lessThanOrEqualTo(createTime, end));
        }

        if (!CollectionUtils.isEmpty(stores)) {
            predicates.add(storeJoin.get(Store_.id).in(stores));
        }

        return predicates;
    }
}
