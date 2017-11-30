package com.yd.manager.utils.jpa;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface SinglePredicateBuilder {

    Predicate getPredicate();
}
