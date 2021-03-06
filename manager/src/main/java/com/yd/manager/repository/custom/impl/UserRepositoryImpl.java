package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.orders.UserOrdersDTO;
import com.yd.manager.dto.orders.UserOrdersDateDTO;
import com.yd.manager.dto.orders.UserStoreOrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.*;
import com.yd.manager.repository.RestrictUtils;
import com.yd.manager.repository.custom.UserDTORepository;
import com.yd.manager.util.TimeUtils;
import com.yd.manager.util.jpa.JpaUtils;
import com.yd.manager.util.jpa.PredicateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static javax.persistence.criteria.JoinType.LEFT;

@Repository
public class UserRepositoryImpl implements UserDTORepository {
    private final EntityManager manager;
    private final CriteriaBuilder builder;

    public UserRepositoryImpl(EntityManager manager) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
    }

    @Override
    public List<UserOrdersDTO> listUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        CriteriaQuery<UserOrdersDTO> criteria = builder.createQuery(UserOrdersDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateBuilder.init(RestrictUtils.restrictForOrders(builder, ordersPath))
                .append(this.restrictForUser(userPath, nameOrPhone))
                .append(this.restrictForOrders(ordersPath, timeRange))
                .append(this.restrictForStore(storePath, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                userPath.get(User_.phone),
                userPath.get(User_.address),
                userPath.get(User_.balance),
                userPath.get(User_.createTime),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.sum(ordersPath.get(Orders_.profit)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        criteria.orderBy(builder.desc(userPath.get(User_.createTime)));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public long countUserOrdersDTO(String nameOrPhone, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<User> userPath = criteria.from(User.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForUser(userPath, nameOrPhone))
                .append(this.restrictForStore(userPath.join(User_.orders, LEFT).join(Orders_.store, LEFT), stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.select(builder.count(userPath));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<UserOrdersDTO> pageUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listUserOrdersDTO(nameOrPhone, timeRange, stores, pageable), pageable, this.countUserOrdersDTO(nameOrPhone, stores));
    }

    @Override
    public List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<UserStoreOrdersDTO> criteria = builder.createQuery(UserStoreOrdersDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateBuilder.init(RestrictUtils.restrictForOrders(builder, ordersPath))
                .append(restrictForUser(userPath, userId))
                .append(restrictForOrders(ordersPath, timeRange))
                .append(restrictForStore(storePath, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath, storePath);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                storePath.get(Store_.id),
                storePath.get(Store_.name),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.sum(ordersPath.get(Orders_.profit)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        criteria.orderBy(builder.asc(storePath.get(Store_.id)));

        return JpaUtils.getResultListByPageable(manager, criteria, null);
    }

    @Override
    public UserOrdersDateDTO getUserOrdersDateDTO(long userId, LocalDate date, List<Long> stores) {
        CriteriaQuery<UserOrdersDateDTO> criteria = builder.createQuery(UserOrdersDateDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateBuilder.init(RestrictUtils.restrictForOrders(builder, ordersPath))
                .append(restrictForUser(userPath, userId))
                .append(restrictForOrders(ordersPath, DateRange.ofDate(date).toTimeRange()))
                .append(restrictForStore(storePath, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath);

        criteria.multiselect(
                builder.literal(TimeUtils.format(date)),
                userPath.get(User_.id),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.sum(ordersPath.get(Orders_.profit)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        return manager.createQuery(criteria).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public long countByCreateTime(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<User> userPath = criteria.from(User.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForUser(userPath, timeRange))
                .append(this.restrictForStore(userPath.join(User_.store), stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.select(builder.count(userPath));

        return manager.createQuery(criteria).getSingleResult();
    }

    private Predicate restrictForUser(Path<User> path, String nameOrPhone) {
        if (StringUtils.hasText(nameOrPhone)) {
            Predicate likeName = builder.like(path.get(User_.name), JpaUtils.matchString(nameOrPhone));
            Predicate likePhone = builder.like(path.get(User_.phone), JpaUtils.matchString(nameOrPhone));
            return builder.or(likeName, likePhone);
        }
        return null;
    }

    private Predicate restrictForUser(Path<User> path, long userId) {
        return builder.equal(path.get(User_.id), userId);
    }

    private Collection<Predicate> restrictForUser(Path<User> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(User_.createTime), timeRange);
    }

    private Collection<Predicate> restrictForOrders(Path<Orders> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(Orders_.createTime), timeRange);
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

}
