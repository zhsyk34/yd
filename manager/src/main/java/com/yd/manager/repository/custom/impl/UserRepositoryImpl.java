package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.UserOrdersDTO;
import com.yd.manager.dto.UserOrdersDateDTO;
import com.yd.manager.dto.UserStoreOrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.UserDTORepository;
import com.yd.manager.util.TimeUtils;
import com.yd.manager.util.jpa.JpaUtils;
import com.yd.manager.util.jpa.PredicateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static javax.persistence.criteria.JoinType.LEFT;

@Repository
public class UserRepositoryImpl implements UserDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserOrdersDTO> listUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserOrdersDTO> criteria = builder.createQuery(UserOrdersDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForUser(builder, userPath, nameOrPhone))
                .append(this.restrictForOrders(builder, ordersPath, timeRange))
                .append(this.restrictForStore(storePath, stores))
                .get();
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
                builder.avg(ordersPath.get(Orders_.actual))
        );

        criteria.orderBy(builder.desc(userPath.get(User_.createTime)));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public List<UserOrdersDTO> listUserOrdersDTO(String nameOrPhone, List<Long> stores, Pageable pageable) {
        return this.listUserOrdersDTO(nameOrPhone, null, stores, pageable);
    }

    @Override
    public long countUserOrdersDTO(String nameOrPhone, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<User> userPath = criteria.from(User.class);

        JpaUtils.setPredicate(criteria, PredicateFactory.instance().append(this.restrictForUser(builder, userPath, nameOrPhone)).get());

        criteria.select(builder.count(userPath));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<UserOrdersDTO> pageUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listUserOrdersDTO(nameOrPhone, timeRange, stores, pageable), pageable, this.countUserOrdersDTO(nameOrPhone, stores));
    }

    @Override
    public List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserStoreOrdersDTO> criteria = builder.createQuery(UserStoreOrdersDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(restrictForUser(builder, userPath, userId))
                .append(restrictForOrders(builder, ordersPath, timeRange))
                .append(restrictForStore(storePath, stores))
                .get();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath, storePath);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                storePath.get(Store_.id),
                storePath.get(Store_.name),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        criteria.orderBy(builder.asc(storePath.get(Store_.id)));

        return JpaUtils.getResultListByPageable(manager, criteria, null);
    }

    @Override
    public UserOrdersDateDTO getUserOrdersDateDTO(long userId, LocalDate date, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserOrdersDateDTO> criteria = builder.createQuery(UserOrdersDateDTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, LEFT);
        Join<Orders, Store> storePath = ordersPath.join(Orders_.store, LEFT);

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(restrictForUser(builder, userPath, userId))
                .append(restrictForOrders(builder, ordersPath, DateRange.ofDate(date).toTimeRange()))
                .append(restrictForStore(storePath, stores))
                .get();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.groupBy(userPath);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                builder.literal(TimeUtils.format(date)),
                //builder.literal(date),//TODO=>ERROR?ValueHandlerFactory::determineAppropriateHandler not support
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        return manager.createQuery(criteria).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public long countByCreateTime(TimeRange timeRange, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<User> userPath = criteria.from(User.class);

        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForUser(builder, userPath, timeRange))
                .append(this.restrictForStore(userPath.join(User_.store), stores))
                .get();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.select(builder.count(userPath));

        return manager.createQuery(criteria).getSingleResult();
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

    private Collection<Predicate> restrictForUser(CriteriaBuilder builder, Path<User> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(User_.createTime), timeRange);
    }

    private Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(Orders_.createTime), timeRange);
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

}
