package com.yd.manager.util.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "instance")
public class PredicateBuilder {

    private final Collection<Predicate> result = new LinkedList<>();

    public static PredicateBuilder init(Predicate predicate) {
        return PredicateBuilder.instance().append(predicate);
    }

    public static PredicateBuilder init(Collection<Predicate> predicates) {
        return PredicateBuilder.instance().append(predicates);
    }

    public PredicateBuilder append(Predicate predicate) {
        Optional.ofNullable(predicate).ifPresent(result::add);
        return this;
    }

    public PredicateBuilder append(Collection<Predicate> predicates) {
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
