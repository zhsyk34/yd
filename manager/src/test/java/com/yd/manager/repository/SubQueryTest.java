//package com.yd.manager.repository;
//
//import com.yd.manager.entity.*;
//import org.junit.Test;
//
//import javax.persistence.criteria.*;
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Transactional
//public class SubQueryTest extends SpringTestInit {
//
//    @Test
//    public void test0() throws Exception {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteria = builder.createQuery();
//
//        Root<Merchandise> merchandiseRoot = criteria.from(Merchandise.class);
//
//        //select count(*) from OrdersMerchandise o inner join OrdersMerchandise.merchandise m on m.id = m.id
//        Subquery<Long> subQuery = criteria.subquery(Long.class);
//        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
//        ordersMerchandiseRoot.join(OrdersMerchandise_.merchandise);
//
//        subQuery.select(builder.count(ordersMerchandiseRoot.get(OrdersMerchandise_.count)));
//
//        criteria.multiselect(merchandiseRoot.get(Merchandise_.name), subQuery.getSelection());
//
//        //select
//        List<Object> list = manager.createQuery(criteria).setFirstResult(0).setMaxResults(10).getResultList();
//
//        show(list);
//    }
//
//    @Test
//    public void test1() throws Exception {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteria = builder.createQuery();
//
//        Root<Merchandise> merchandiseRoot = criteria.from(Merchandise.class);
//
//        //select count(*) from OrdersMerchandise o inner join OrdersMerchandise.merchandise m on m.id = m.id
//        //where m.id = merchandiseRoot(m).id
//        Subquery<Long> subQuery = criteria.subquery(Long.class);
//        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
//        Join<OrdersMerchandise, Merchandise> join = ordersMerchandiseRoot.join(OrdersMerchandise_.merchandise);
//
//        Predicate predicate = builder.equal(join.get(Merchandise_.id), merchandiseRoot.get(Merchandise_.id));
//        subQuery.select(builder.count(ordersMerchandiseRoot.get(OrdersMerchandise_.count))).where(predicate);
//
//        criteria.multiselect(merchandiseRoot.get(Merchandise_.name), subQuery.getSelection());
//
//        //select
//        List<Object> list = manager.createQuery(criteria).setFirstResult(0).setMaxResults(10).getResultList();
//
//        show(list);
//    }
//
//    @Test
//    public void test2() throws Exception {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteria = builder.createQuery();
//
//        Root<Merchandise> merchandiseRoot = criteria.from(Merchandise.class);
//
//        Subquery<Long> subQuery = criteria.subquery(Long.class);
//        Root<OrdersMerchandise> subRoot = subQuery.from(OrdersMerchandise.class);
//
//        Expression<Long> e1 = builder.sum(subRoot.get(OrdersMerchandise_.count));
//
//        subQuery.select(e1);
//
//        subQuery.where(builder.equal(merchandiseRoot.get(Merchandise_.id), merchandiseRoot.get(Merchandise_.id)));
//
//        criteria.multiselect(merchandiseRoot.get(Merchandise_.name), subQuery.getSelection());
//
//        List<Object> list = manager.createQuery(criteria).setFirstResult(0).setMaxResults(10).getResultList();
//
//        show(list);
//    }
//
//    @Test
//    public void test3() throws Exception {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteria = builder.createQuery();
//
//        Root<Merchandise> root = criteria.from(Merchandise.class);
//
//        Subquery<Long> subQuery = criteria.subquery(Long.class);
//        //sub from
//        Root<OrdersMerchandise> subRoot = subQuery.from(OrdersMerchandise.class);
//        //sub select
//        subQuery.select(builder.count(subRoot.get(OrdersMerchandise_.count)));
//
//        Root<Merchandise> correlate = subQuery.correlate(root);
//
//        //the same,wrong!
//        subQuery.where(builder.equal(correlate.get(Merchandise_.id), root.get(Merchandise_.id)));
//
//        criteria.multiselect(root.get(Merchandise_.name), subQuery.getSelection());
//
//        List<Object> list = manager.createQuery(criteria).setFirstResult(0).setMaxResults(10).getResultList();
//
//        show(list);
//    }
//
//    private void show(List<?> list) {
//        for (Object o : list) {
//            if (o instanceof Object[]) {
//                Object[] os = (Object[]) o;
//                for (Object oo : os) {
//                    System.out.print(oo + " ");
//                }
//                System.out.println();
//            } else {
//                System.out.println(o);
//            }
//        }
//    }
//
////    @Test
////    @SkipForDialect(value = SybaseASE15Dialect.class, jiraKey = "HHH-3032")
////    public void testCorrelationExplicitSelectionCorrelation() {
////        CriteriaBuilder builder = manager.getCriteriaBuilder();
////        manager.getTransaction().begin();
////
////        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
////        Root<Customer> customer = criteria.from(Customer.class);
////        Join<Customer, Order> o = customer.join(Customer_.orders);
////
////        Subquery<Order> sq = criteria.subquery(Order.class);
////        Join<Customer, Order> sqo = sq.correlate(o);
////        Join<Order, LineItem> sql = sqo.join(Order_.lineItems);
////        sq.where(builder.gt(sql.get(LineItem_.quantity), 3));
////        // use the correlation itself as the subquery selection (initially caused problems wrt aliases)
////        sq.select(sqo);
////        criteria.select(customer).distinct(true);
////        criteria.where(builder.exists(sq));
////        manager.createQuery(criteria).getResultList();
////
////        manager.getTransaction().commit();
////        manager.close();
////    }
//
////    @Test
////    public void testEqualAll() {
////        CriteriaBuilder builder = manager.getCriteriaBuilder();
////        manager.getTransaction().begin();
////
////        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
////        Root<Customer> customerRoot = criteria.from(Customer.class);
////        Join<Customer, Order> orderJoin = customerRoot.join(Customer_.orders);
////        criteria.select(customerRoot);
////
////        Subquery<Double> subCriteria = criteria.subquery(Double.class);
////        Root<Order> subqueryOrderRoot = subCriteria.from(Order.class);
////        subCriteria.select(builder.min(subqueryOrderRoot.get(Order_.totalPrice)));
////        criteria.where(builder.equal(orderJoin.get("totalPrice"), builder.all(subCriteria)));
////        manager.createQuery(criteria).getResultList();
////
////        manager.getTransaction().commit();
////        manager.close();
////    }
//}
