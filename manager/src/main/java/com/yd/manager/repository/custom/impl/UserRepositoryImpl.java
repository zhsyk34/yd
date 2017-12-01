package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.*;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.UserDTORepository;
import com.yd.manager.utils.TimeUtils;
import com.yd.manager.utils.jpa.JpaUtils;
import com.yd.manager.utils.jpa.PredicateFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.criteria.JoinType.LEFT;

@Repository
public class UserRepositoryImpl implements UserDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserOrderCollectDTO> listUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserOrderCollectDTO> criteria = builder.createQuery(UserOrderCollectDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                userPath.get(User_.phone),
                userPath.get(User_.address),
                userPath.get(User_.balance),
                userPath.get(User_.createTime),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(restrictForUser(builder, userPath, nameOrPhone))
                .append(restrictForOrders(builder, ordersPath, timeRange))
                .append(restrictForStore(storePath, stores))
                .get();

        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath);

        criteria.orderBy(builder.desc(userPath.get(User_.createTime)));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public long countUserOrderCollectDTO(String nameOrPhone, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<User> userPath = criteria.from(User.class);

        criteria.select(builder.count(userPath));

        JpaUtils.setPredicate(criteria, PredicateFactory.instance().append(restrictForUser(builder, userPath, nameOrPhone)).get());

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<UserOrderCollectDTO> pageUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listUserOrderCollectDTO(nameOrPhone, timeRange, stores, pageable), pageable, this.countUserOrderCollectDTO(nameOrPhone, stores));
    }

    @Override
    public List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserStoreOrdersDTO> criteria = builder.createQuery(UserStoreOrdersDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                storePath.get(Store_.id),
                storePath.get(Store_.name),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(restrictForUser(builder, userPath, userId))
                .append(restrictForOrders(builder, ordersPath, timeRange))
                .append(restrictForStore(storePath, stores))
                .get();

        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath, storePath);

        criteria.orderBy(builder.asc(storePath.get(Store_.id)));

        return JpaUtils.getResultListByPageable(manager, criteria, null);
    }

    @Override
    public UserOrderCollectByDateDTO getUserOrderCollectByDateDTO(long userId, LocalDate day, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserOrderCollectByDateDTO> criteria = builder.createQuery(UserOrderCollectByDateDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                builder.literal(TimeUtils.format(day)),
//                builder.literal(day),//TODO=>ERROR?ValueHandlerFactory::determineAppropriateHandler not support
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(restrictForUser(builder, userPath, userId))
                .append(restrictForOrders(builder, ordersPath, DateRange.day(day).toTimeRange()))
                .append(restrictForStore(storePath, stores))
                .get();

        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath);

        return manager.createQuery(criteria).getResultList().stream().findFirst().orElse(null);
    }

    private Predicate restrictForUser(CriteriaBuilder builder, Path<User> path, String nameOrPhone) {
        if (StringUtils.hasText(nameOrPhone)) {
            Predicate likeName = builder.like(path.get(User_.name), JpaUtils.matchString(nameOrPhone));
            Predicate likePhone = builder.like(path.get(User_.phone), JpaUtils.matchString(nameOrPhone));
            return builder.or(likeName, likePhone);
        }
        return null;
    }

    private Predicate restrictForUser(CriteriaBuilder builder, Path<User> path, long userId) {
        return builder.equal(path.get(User_.id), userId);
    }

    private Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> path, TimeRange timeRange) {
        if (timeRange == null) {
            return null;
        }

        Collection<Predicate> predicates = new LinkedList<>();
        Optional.ofNullable(timeRange.getBegin()).map(begin -> builder.greaterThanOrEqualTo(path.get(Orders_.createTime), begin)).ifPresent(predicates::add);
        Optional.ofNullable(timeRange.getEnd()).map(end -> builder.lessThanOrEqualTo(path.get(Orders_.createTime), end)).ifPresent(predicates::add);
        return predicates;
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

}
