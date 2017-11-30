package com.yd.manager.utils.jpa;

import javax.persistence.criteria.Predicate;
import java.util.List;

@FunctionalInterface
public interface PredicateBuilder {
    List<Predicate> getPredicates();
}
