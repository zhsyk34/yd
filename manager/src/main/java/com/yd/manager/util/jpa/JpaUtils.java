package com.yd.manager.util.jpa;

import com.yd.manager.dto.util.TimeRange;
import org.hibernate.criterion.MatchMode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.MatchMode.ANYWHERE;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SuppressWarnings("WeakerAccess")
public abstract class JpaUtils {

    public static List<Order> getOrderFromSort(CriteriaBuilder builder, Path<?> path, Sort sort) {
        return Optional.ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false).map(o -> getOrder(builder, path, o)).filter(Objects::nonNull).collect(toList())).orElse(emptyList());
    }

    public static Order getOrder(CriteriaBuilder builder, Path<?> path, Sort.Order order) {
        return Optional.ofNullable(order).map(o -> path.get(o.getProperty())).map(field -> order.getDirection() == ASC ? builder.asc(field) : builder.desc(field)).orElse(null);
    }

    public static String matchString(String s) {
        return matchString(s, ANYWHERE);
    }

    public static String matchString(String s, MatchMode mode) {
        return mode.toMatchString(s);
    }

    @Deprecated
    public static <T> Predicate like(CriteriaBuilder builder, Path<T> path, SingularAttribute<? super T, String> attribute, String value) {
        return builder.like(path.get(attribute), matchString(value));
    }

    public static Collection<Predicate> between(CriteriaBuilder builder, Path<LocalDateTime> path, TimeRange timeRange) {
        if (timeRange == null) {
            return null;
        }

        Collection<Predicate> predicates = new LinkedList<>();
        Optional.ofNullable(timeRange.getBegin()).map(begin -> builder.greaterThanOrEqualTo(path, begin)).ifPresent(predicates::add);
        Optional.ofNullable(timeRange.getEnd()).map(end -> builder.lessThanOrEqualTo(path, end)).ifPresent(predicates::add);
        return predicates;
    }

    public static void setOrderByPageable(CriteriaQuery<?> criteria, Pageable pageable, OrderBuilder orderBuilder) {
        List<Order> orders = Optional.ofNullable(pageable).map(Pageable::getSort).map(sort -> orderBuilder.getOrders()).orElse(null);
        setOrder(criteria, orders);
    }

    public static void setOrder(CriteriaQuery<?> criteria, List<Order> orders) {
        if (!CollectionUtils.isEmpty(orders)) {
            criteria.orderBy(orders);
        }
    }

    public static void setPredicateByPredicateBuilder(CriteriaQuery<?> criteria, PredicateBuilder predicateBuilder) {
        setPredicate(criteria, predicateBuilder.getPredicates());
    }

    public static void setPredicate(CriteriaQuery<?> criteria, Collection<Predicate> predicates) {
        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    public static void setPredicateBySinglePredicateBuilder(CriteriaQuery<?> criteria, SinglePredicateBuilder predicateBuilder) {
        setSinglePredicate(criteria, predicateBuilder.getPredicate());
    }

    public static void setSinglePredicate(CriteriaQuery<?> criteria, Predicate predicate) {
        Optional.ofNullable(predicate).ifPresent(criteria::where);
    }

    /**
     * 根据分页信息进行查询,其余条件均已设置
     *
     * @param manager  会话
     * @param pageable 分页信息
     * @param <T>      结果类型
     * @return 结果集
     */
    public static <T> List<T> getResultListByPageable(EntityManager manager, CriteriaQuery<T> criteria, Pageable pageable) {
        TypedQuery<T> query = manager.createQuery(criteria);

        Optional.ofNullable(pageable).ifPresent(p -> query.setFirstResult(p.getOffset()).setMaxResults(p.getPageSize()));

        return query.getResultList();
    }

}
