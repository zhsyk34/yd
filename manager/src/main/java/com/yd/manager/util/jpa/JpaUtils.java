package com.yd.manager.util.jpa;

import com.yd.manager.dto.util.TimeRange;
import org.hibernate.criterion.MatchMode;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.criterion.MatchMode.ANYWHERE;

@SuppressWarnings("WeakerAccess")
public abstract class JpaUtils {

    public static String matchString(String s) {
        return matchString(s, ANYWHERE);
    }

    public static String matchString(String s, MatchMode mode) {
        return mode.toMatchString(s);
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

    public static void setPredicate(CriteriaQuery<?> criteria, Collection<Predicate> predicates) {
        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }
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
