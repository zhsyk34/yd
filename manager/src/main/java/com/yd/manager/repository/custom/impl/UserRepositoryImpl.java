package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.UserDTO;
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
    public List<UserDTO> findUserDTO(String name, String phone, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserDTO> criteria = builder.createQuery(UserDTO.class);

        Root<Orders> ordersRoot = criteria.from(Orders.class);
        Join<Orders, User> userJoin = ordersRoot.join(Orders_.user);

        criteria.multiselect(
                userJoin.get(User_.id),
                userJoin.get(User_.name),
                userJoin.get(User_.phone),
                userJoin.get(User_.address),
                userJoin.get(User_.balance),
                userJoin.get(User_.createTime),
                builder.literal(10),//TODO
                builder.count(ordersRoot.get(Orders_.id)),
                builder.sum(ordersRoot.get(Orders_.actual)),
                builder.avg(ordersRoot.get(Orders_.actual))
        );

        criteria.groupBy(userJoin);

        if (pageable != null) {
            criteria.orderBy(from(builder, ordersRoot, pageable.getSort()));
        }

        TypedQuery<UserDTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

//    @Override
//    public List<UserDTO> findUserDTO(String name, String phone, List<Long> stores) {
//        Query query = manager.createNativeQuery("SELECT u.id, u.user_nickname, u.user_address, u.create_time, b.actual " +
//                "FROM estore_user u INNER JOIN " +
//                "(SELECT buyer_uid AS uid, sum(paid_cost) AS actual FROM estore_order o GROUP BY buyer_uid) AS b " +
//                "ON b.uid = u.id");
//
//        List list = query.getResultList();
//        for (Object o : list) {
//            if (o instanceof Object[]) {
//                Object[] os = (Object[]) o;
//                for (Object o1 : os) {
//                    System.out.print(o1 + " ");
//                }
//                System.out.println();
//            }
//        }
//
//        return null;
//    }

//    @Override
//    public List<UserDTO> findUserDTO(String name, String phone, List<Long> stores) {
//        Session session = manager.unwrap(Session.class);
//        Query query = session.createQuery("select u.id as id, u.name as name, u.phone, u.address, u.balance, u.createTime, " +
//                "1 as count, count(o.id) as payCount, sum(o.actual) as paySum, avg(o.actual) as payAvg " +
//                "from User u join Orders o on o.user = u group by u")
//                .setResultTransformer(Transformers.aliasToBean(UserDTO.class));
//
//        return query.getResultList();
//    }
}
