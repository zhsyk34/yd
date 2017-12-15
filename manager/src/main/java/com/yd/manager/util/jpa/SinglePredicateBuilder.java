package com.yd.manager.util.jpa;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface SinglePredicateBuilder {

    Predicate getPredicate();
}
