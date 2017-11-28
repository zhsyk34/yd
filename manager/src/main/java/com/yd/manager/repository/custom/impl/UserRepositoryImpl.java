package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.UserOrder2DTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.UserDTORepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

import static com.yd.manager.utils.JpaUtils.from;

@Repository
public class UserRepositoryImpl implements UserDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserOrder2DTO> findUserOrder2DTO(String name, String phone, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserOrder2DTO> criteria = builder.createQuery(UserOrder2DTO.class);

        Root<User> userPath = criteria.from(User.class);
        SetJoin<User, Orders> ordersPath = userPath.join(User_.orders, JoinType.LEFT);

        criteria.multiselect(
                userPath.get(User_.id),
                userPath.get(User_.name),
                userPath.get(User_.phone),
                userPath.get(User_.address),
                userPath.get(User_.balance),
                userPath.get(User_.createTime),
                builder.count(ordersPath.get(Orders_.id)),
                builder.sum(ordersPath.get(Orders_.actual)),
                builder.avg(ordersPath.get(Orders_.actual))
        );

        criteria.groupBy(userPath);

        if (pageable != null) {
            criteria.orderBy(from(builder, ordersPath, pageable.getSort()));
        }

        TypedQuery<UserOrder2DTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }
}
