package com.yd.manager.utils.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;

@RequiredArgsConstructor(staticName = "instance")
public class PredicateFactory {

    private final Collection<Predicate> result = new LinkedList<>();

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

    public Collection<Predicate> get() {
        return result;
    }
}
