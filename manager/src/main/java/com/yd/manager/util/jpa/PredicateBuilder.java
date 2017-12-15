package com.yd.manager.util.jpa;

import javax.persistence.criteria.Predicate;
import java.util.List;

@FunctionalInterface
public interface PredicateBuilder {
    List<Predicate> getPredicates();
}
