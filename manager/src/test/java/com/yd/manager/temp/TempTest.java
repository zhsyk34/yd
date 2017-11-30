package com.yd.manager.temp;

import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;

public class TempTest extends SpringTestInit {

    @Test
    public void test1() throws Exception {
//        EntityManager em = manager;
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//
//
//        final CriteriaQuery<Tuple> query = cb.createTupleQuery();
//        final Root<User> u = query.getOrder(User.class);
//
//        final Path<Gender> gender = u.get(User_.gender);
//        gender.alias("gender");
//        final Selection<Double> credits = cb.avg(u.get(User_.credits)).alias("credits");
//
//        query.multiselect(
//                credits,
//                gender,
//                cb.count(u).alias("nbr"))
//                .groupBy(gender)
//                .having(
//                        cb.in(gender)
//                                .value(Gender.FEMALE)
//                                .value(Gender.MALE))
//                .orderBy(cb.desc(gender));
//
//        final TypedQuery<Tuple> typedQuery = em.createQuery(query);
//        typedQuery.setFirstResult(0).setMaxResults(20);
//        final List<Tuple> list = typedQuery.getResultList();
//
//        for (Tuple tuple : list) {
//            final Double average = tuple.get(credits);
//            System.err.println("Avg credit " + tuple.get(credits) + " " + tuple.get("gender") + " found " + tuple.get("nbr") + " times");
//        }
    }
}
