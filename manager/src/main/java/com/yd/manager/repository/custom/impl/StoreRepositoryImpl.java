package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.StoreOrdersDTO;
import com.yd.manager.dto.StoreOrdersDateDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.Orders;
import com.yd.manager.entity.Orders_;
import com.yd.manager.entity.Store;
import com.yd.manager.entity.Store_;
import com.yd.manager.repository.custom.StoreDTORepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public class StoreRepositoryImpl implements StoreDTORepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<StoreOrdersDTO> listStoreOrdersDTO(String nameOrCode, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<StoreOrdersDTO> criteria = builder.createQuery(StoreOrdersDTO.class);

        //from
        Root<Store> storeRoot = criteria.from(Store.class);
        SetJoin<Store, Orders> ordersJoin = storeRoot.join(Store_.orders, JoinType.LEFT);

        //where
        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForStore(builder, storeRoot, nameOrCode))
                .append(this.restrictForStore(storeRoot, stores))
                .append(this.restrictForOrders(builder, ordersJoin, timeRange))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        //group by
        criteria.groupBy(storeRoot);

        Expression<BigDecimal> sum = builder.sum(ordersJoin.get(Orders_.actual));
        criteria.multiselect(
                storeRoot.get(Store_.id),
                storeRoot.get(Store_.name),
                storeRoot.get(Store_.address),
                builder.count(ordersJoin),
                sum,
                builder.avg(ordersJoin.get(Orders_.actual))
        );

        //order by
        criteria.orderBy(builder.desc(sum));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public List<StoreOrdersDTO> listStoreOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return this.listStoreOrdersDTO(nameOrCode, null, stores, pageable);
    }

    @Override
    public long countStoreOrdersDTO(String nameOrCode, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        //from
        Root<Store> storePath = criteria.from(Store.class);

        //where
        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForStore(builder, storePath, nameOrCode))
                .append(this.restrictForStore(storePath, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        criteria.select(builder.count(storePath));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<StoreOrdersDTO> pageStoreOrdersDTO(String nameOrCode, TimeRange timeRange, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listStoreOrdersDTO(nameOrCode, timeRange, stores, pageable), pageable, this.countStoreOrdersDTO(nameOrCode, stores));
    }

    @Override
    public StoreOrdersDTO getStoreOrdersDTO(long storeId, TimeRange timeRange) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<StoreOrdersDTO> criteria = builder.createQuery(StoreOrdersDTO.class);

        //from
        Root<Store> storeRoot = criteria.from(Store.class);
        SetJoin<Store, Orders> ordersJoin = storeRoot.join(Store_.orders, JoinType.LEFT);

        //where
        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForStore(builder, storeRoot, storeId))
                .append(this.restrictForOrders(builder, ordersJoin, timeRange))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        //group by
        criteria.groupBy(storeRoot);

        criteria.multiselect(
                storeRoot.get(Store_.id),
                storeRoot.get(Store_.name),
                storeRoot.get(Store_.address),
                builder.count(ordersJoin),
                builder.sum(ordersJoin.get(Orders_.actual)),
                builder.avg(ordersJoin.get(Orders_.actual))
        );

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public StoreOrdersDateDTO getStoreOrdersDateDTO(long storeId, LocalDate date) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<StoreOrdersDateDTO> criteria = builder.createQuery(StoreOrdersDateDTO.class);

        //from
        Root<Store> storeRoot = criteria.from(Store.class);
        SetJoin<Store, Orders> ordersJoin = storeRoot.join(Store_.orders, JoinType.LEFT);

        //where
        Collection<Predicate> predicates = PredicateFactory.instance()
                .append(this.restrictForStore(builder, storeRoot, storeId))
                .append(this.restrictForOrders(builder, ordersJoin, DateRange.ofDate(date).toTimeRange()))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        //group by
        criteria.groupBy(storeRoot);

        criteria.multiselect(
                storeRoot.get(Store_.id),
                storeRoot.get(Store_.name),
                builder.literal(TimeUtils.format(date)),
                builder.count(ordersJoin),
                builder.sum(ordersJoin.get(Orders_.actual)),
                builder.avg(ordersJoin.get(Orders_.actual))
        );

        return manager.createQuery(criteria).getResultList().stream().findFirst().orElse(null);
    }

    private Predicate restrictForStore(CriteriaBuilder builder, Path<Store> path, String nameOrCode) {
        if (StringUtils.hasText(nameOrCode)) {
            Predicate likeName = builder.like(path.get(Store_.name), JpaUtils.matchString(nameOrCode));
            Predicate likeCode = builder.like(path.get(Store_.code), JpaUtils.matchString(nameOrCode));
            return builder.or(likeName, likeCode);
        }
        return null;
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

    private Collection<Predicate> restrictForOrders(CriteriaBuilder builder, Path<Orders> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(Orders_.createTime), timeRange);
    }

    private Predicate restrictForStore(CriteriaBuilder builder, Path<Store> path, long storeId) {
        return builder.equal(path.get(Store_.id), storeId);
    }
}
