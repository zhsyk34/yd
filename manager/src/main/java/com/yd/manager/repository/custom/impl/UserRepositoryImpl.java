package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.UserDTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.UserDTORepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

//TODO
@Repository
public class UserRepositoryImpl implements UserDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserDTO> findUserDTO(String name, String phone, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserDTO> criteria = builder.createQuery(UserDTO.class);

        Root<User> userRoot = criteria.from(User.class);
        Join<User, Store> userStoreJoin = userRoot.join(User_.store);

//        Root<Orders> ordersRoot = criteria.from(Orders.class);
//        Join<Orders, Store> ordersStoreFetch = ordersRoot.join(Orders_.store);
//
//        Path<Long> p1 = userStoreJoin.get(Store_.id);
//        Path<Long> p2 = ordersStoreFetch.get(Store_.id);
//
//        criteria.where(builder.equal(p1, p2));

        Subquery<Orders> subCriteria = criteria.subquery(Orders.class);
        Root<Orders> ordersRoot = subCriteria.from(Orders.class);
//        subCriteria.correlate()  ;
//        subCriteria.where(builder.equal(userStoreJoin.get(Store_.id)));
        subCriteria.groupBy();

        criteria.multiselect(
                userRoot.get(User_.name),
                userRoot.get(User_.phone),
                userStoreJoin.get(Store_.name),
                userRoot.get(User_.balance),
                userRoot.get(User_.createTime),
                builder.literal(10),
                builder.count(ordersRoot.get(Orders_.id)),
                builder.sum(ordersRoot.get(Orders_.actual)),
                builder.avg(ordersRoot.get(Orders_.actual))
        );

        System.err.println("------------UserDTORepository------------");
        return manager.createQuery(criteria).getResultList();
    }
}
