package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.record.AccessRecordCount;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.AccessRecordDTORepository;
import com.yd.manager.util.TimeUtils;
import com.yd.manager.util.jpa.JpaUtils;
import com.yd.manager.util.jpa.PredicateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@Repository
public class AccessRecordRepositoryImpl implements AccessRecordDTORepository {
    private final EntityManager manager;
    private final CriteriaBuilder builder;

    public AccessRecordRepositoryImpl(EntityManager manager) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
    }

    @Override
    public long countEnter(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.select(builder.count(path))).getSingleResult();
    }

    @Override
    public long countEntrant(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.select(builder.count(path))).getSingleResult();
    }

    @Override
    public long countValid(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.select(builder.count(path))).getSingleResult();
    }

    @Override
    public AccessRecordCount countEnterByUser(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(userId), builder.count(path))).getSingleResult();
    }

    @Override
    public AccessRecordCount countEntrantByUser(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(userId), builder.count(path))).getSingleResult();
    }

    @Override
    public AccessRecordCount countValidByUser(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(userId), builder.count(path))).getSingleResult();
    }

    @Override
    public List<AccessRecordCount> listCountEnterGroupByUser(List<Long> users, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUsers(path, users))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> userPath = path.get(AccessRecord_.user).get(User_.id);
        criteria.groupBy(userPath);

        return manager.createQuery(criteria.multiselect(userPath, builder.count(path))).getResultList();
    }

    @Override
    public List<AccessRecordCount> listCountEntrantGroupByUser(List<Long> users, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUsers(path, users))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> userPath = path.get(AccessRecord_.user).get(User_.id);
        criteria.groupBy(userPath);

        return manager.createQuery(criteria.multiselect(userPath, builder.count(path))).getResultList();
    }

    @Override
    public List<AccessRecordCount> listCountValidGroupByUser(List<Long> users, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForUsers(path, users))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> userPath = path.get(AccessRecord_.user).get(User_.id);
        criteria.groupBy(userPath);

        return manager.createQuery(criteria.multiselect(userPath, builder.count(path))).getResultList();
    }

    @Override
    public AccessRecordCount countEnterByStore(long storeId, TimeRange timeRange) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForStore(path, storeId))
                .append(this.restrictForAccess(path, timeRange))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(storeId), builder.count(path))).getSingleResult();
    }

    @Override
    public AccessRecordCount countEntrantByStore(long storeId, TimeRange timeRange) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForStore(path, storeId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(storeId), builder.count(path))).getSingleResult();
    }

    @Override
    public AccessRecordCount countValidByStore(long storeId, TimeRange timeRange) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForStore(path, storeId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        return manager.createQuery(criteria.multiselect(builder.literal(storeId), builder.count(path))).getSingleResult();
    }

    @Override
    public List<AccessRecordCount> listCountEnterGroupByStore(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        Expression<Long> countColumn = builder.count(path);
        criteria.orderBy(builder.desc(countColumn));

        return manager.createQuery(criteria.multiselect(storePath, countColumn)).getResultList();
    }

    @Override
    public List<AccessRecordCount> listCountEntrantGroupByStore(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        return manager.createQuery(criteria.multiselect(storePath, builder.count(path))).getResultList();
    }

    @Override
    public List<AccessRecordCount> listCountValidGroupByStore(TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        return manager.createQuery(criteria.multiselect(storePath, builder.count(path))).getResultList();
    }

    @Override
    public List<AccessRecordCount> listEnterByUserAndGroupByStore(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        Expression<Long> countColumn = builder.count(path);
        criteria.orderBy(builder.desc(countColumn));

        return manager.createQuery(criteria.multiselect(storePath, countColumn)).getResultList();
    }

    @Override
    public List<AccessRecordCount> listEntrantByUserAndGroupByStore(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.init(this.restrictForAccessEnter(path))
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessNew(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        return manager.createQuery(criteria.multiselect(storePath, builder.count(path))).getResultList();
    }

    @Override
    public List<AccessRecordCount> listValidByUserAndGroupByStore(long userId, TimeRange timeRange, List<Long> stores) {
        CriteriaQuery<AccessRecordCount> criteria = builder.createQuery(AccessRecordCount.class);

        Root<AccessRecord> path = criteria.from(AccessRecord.class);

        Collection<Predicate> predicates = PredicateBuilder.instance()
                .append(this.restrictForUser(path, userId))
                .append(this.restrictForAccess(path, timeRange))
                .append(this.restrictForStores(path, stores))
                .append(this.restrictForAccessValid(path))
                .build();
        JpaUtils.setPredicate(criteria, predicates);

        Path<Long> storePath = path.get(AccessRecord_.store).get(Store_.id);
        criteria.groupBy(storePath);

        return manager.createQuery(criteria.multiselect(storePath, builder.count(path))).getResultList();
    }

    private Predicate restrictForUser(Path<AccessRecord> path, long userId) {
        return userId > 0 ? builder.equal(path.get(AccessRecord_.user).get(User_.id), userId) : null;
    }

    private Predicate restrictForUsers(Path<AccessRecord> path, List<Long> users) {
        return CollectionUtils.isEmpty(users) ? null : path.get(AccessRecord_.user).get(User_.id).in(users);
    }

    private Predicate restrictForStore(Path<AccessRecord> path, long storeId) {
        return storeId > 0 ? builder.equal(path.get(AccessRecord_.store).get(Store_.id), storeId) : null;
    }

    private Predicate restrictForStores(Path<AccessRecord> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(AccessRecord_.store).get(Store_.id).in(stores);
    }

    private Collection<Predicate> restrictForAccess(Path<AccessRecord> path, TimeRange timeRange) {
        return JpaUtils.between(builder, path.get(AccessRecord_.enterTime), timeRange);
    }

    private Predicate restrictForAccessEnter(Path<AccessRecord> path) {
        return builder.greaterThan(path.get(AccessRecord_.enterTime), TimeUtils.parseSecond(0));
    }

    private Predicate restrictForAccessNew(Path<AccessRecord> path) {
        return builder.equal(path.get(AccessRecord_.newUser), true);
    }

    private Predicate restrictForAccessValid(Path<AccessRecord> path) {
        return builder.greaterThan(path.get(AccessRecord_.orders).get(Orders_.id), 0L);
    }
}
