package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.UserOrdersDTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.OrdersDTORepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yd.manager.utils.JpaUtils.from;

@Repository
public class OrdersRepositoryImpl implements OrdersDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserOrdersDTO> findUserOrderDTO(Long userId, LocalDateTime begin, LocalDateTime end, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserOrdersDTO> criteria = builder.createQuery(UserOrdersDTO.class);

        Root<Orders> ordersRoot = criteria.from(Orders.class);
        Join<Orders, User> userJoin = ordersRoot.join(Orders_.user);
        Join<Orders, Store> storeJoin = ordersRoot.join(Orders_.store);

        criteria.multiselect(
                userJoin.get(User_.id),
                userJoin.get(User_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                ordersRoot.get(Orders_.id),
                ordersRoot.get(Orders_.actual)
        );

        List<Predicate> predicates = predicates(builder, ordersRoot, userJoin, storeJoin, userId, begin, end, stores);

        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        if (pageable != null) {
            criteria.orderBy(from(builder, ordersRoot, pageable.getSort()));
        }

        TypedQuery<UserOrdersDTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    private List<Predicate> predicates(CriteriaBuilder builder, Root<Orders> ordersRoot, Join<?, User> userJoin, Join<?, Store> storeJoin, Long userId, LocalDateTime begin, LocalDateTime end, List<Long> stores) {
        List<Predicate> predicates = new ArrayList<>();
        if (userId != null) {
            predicates.add(builder.equal(userJoin.get(User_.id), userId));
        }

        Path<LocalDateTime> createTime = ordersRoot.get(Orders_.createTime);
        if (begin != null) {
            predicates.add(builder.greaterThanOrEqualTo(createTime, begin));
        }
        if (end != null) {
            predicates.add(builder.lessThanOrEqualTo(createTime, end));
        }

        if (!CollectionUtils.isEmpty(stores)) {
            predicates.add(storeJoin.get(Store_.id).in(stores));
        }

        return predicates;
    }
}
