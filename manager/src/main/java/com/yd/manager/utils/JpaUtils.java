package com.yd.manager.utils;

import org.hibernate.criterion.MatchMode;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.MatchMode.ANYWHERE;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SuppressWarnings("WeakerAccess")
public abstract class JpaUtils {

    public static Order from(CriteriaBuilder builder, Path<?> path, Sort.Order order) {
        return Optional.ofNullable(order).map(o -> path.get(o.getProperty())).map(field -> order.getDirection() == ASC ? builder.asc(field) : builder.desc(field)).orElse(null);
    }

    public static List<Order> from(CriteriaBuilder builder, Path<?> path, Sort sort) {
        return Optional.ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false).map(o -> from(builder, path, o)).filter(Objects::nonNull).collect(toList())).orElse(emptyList());
    }

    public static String matchString(String s, MatchMode mode) {
        return mode.toMatchString(s);
    }

    public static String matchString(String s) {
        return matchString(s, ANYWHERE);
    }

}
