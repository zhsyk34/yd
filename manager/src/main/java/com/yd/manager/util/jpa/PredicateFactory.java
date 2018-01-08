package com.yd.manager.util.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor(staticName = "instance")
public class PredicateFactory {

    private final Collection<Predicate> result = new LinkedList<>();

    public static PredicateFactory init(Predicate predicate) {
        return PredicateFactory.instance().append(predicate);
    }

    public static PredicateFactory init(Collection<Predicate> predicates) {
        return PredicateFactory.instance().append(predicates);
    }

    public PredicateFactory append(Predicate predicate) {
        Optional.ofNullable(predicate).ifPresent(result::add);
        return this;
    }

    public PredicateFactory append(Collection<Predicate> predicates) {
        if (CollectionUtils.isEmpty(predicates)) {
            return this;
        }
        predicates.stream().filter(Objects::nonNull).forEach(result::add);
        return this;
    }

    public Collection<Predicate> build() {
        return result;
    }
}
