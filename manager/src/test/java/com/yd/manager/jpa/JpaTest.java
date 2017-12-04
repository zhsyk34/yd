package com.yd.manager.jpa;

import com.yd.manager.Application;
import com.yd.manager.entity.Manager;
import com.yd.manager.entity.Manager_;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

//https://www.ibm.com/developerworks/cn/java/j-typesafejpa/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Slf4j
public class JpaTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager manager;

    @Test
    public void test1() throws Exception {
        EntityManager entityManager = factory.createEntityManager();
        System.err.println(entityManager.hashCode());
        System.err.println(manager.hashCode());

        System.err.println(entityManager == manager);//false
    }

    @Test
    public void test2() throws Exception {
        CriteriaBuilder builder = factory.getCriteriaBuilder();
        CriteriaBuilder builder1 = manager.getCriteriaBuilder();
        System.err.println(builder == builder1);//true
    }

    @Test
    public void test3() throws Exception {
        //Bindable(可绑定属性) + Type(可持久化属性) ==> ManagedType(属性映射?) ==> IdentifiableType(主键/版本?)
        Metamodel metamodel = manager.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        entities.forEach(et -> {
            log.info("name:{}", et.getName());
            log.info("type:{}", et.getPersistenceType());

            et.getAttributes().forEach(at -> log.info("name:{},type:{},javaType:{}", at.getName(), at.getDeclaringType(), at.getJavaType()));
        });

    }

    @Test
    public void test4() throws Exception {
        Metamodel metamodel = manager.getMetamodel();
        EntityType<Manager> managerEntityType = metamodel.entity(Manager.class);

        System.out.println(managerEntityType.getName());
        Type<?> idType = managerEntityType.getIdType();
        System.out.println(idType.getPersistenceType());

        SingularAttribute<? super Manager, ?> id = managerEntityType.getId(idType.getJavaType());
        System.out.println(id.getName());
    }

    @Test
    public void test5() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Manager> criteria = builder.createQuery(Manager.class);

        Root<Manager> root = criteria.from(Manager.class);
        System.out.println(root.getAlias());//null

        Path<Long> idPath = root.get(Manager_.id);

        In<Long> in = builder.in(idPath);
        in.value(1L);
        in.value(2L);

        criteria.where(in);

        TypedQuery<Manager> query = manager.createQuery(criteria);
        List<Manager> list = query.getResultList();

        list.forEach(System.out::println);
    }

    @Test
    public void test51() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Manager> root = criteria.from(Manager.class);

        criteria.multiselect(root, builder.literal(1));

        Path<Long> idPath = root.get(Manager_.id);
        In<Long> in = builder.in(idPath).value(1L).value(2L);
        criteria.where(in);

        TypedQuery<Object[]> query = manager.createQuery(criteria);
        List<Object[]> list = query.getResultList();

        list.forEach(System.out::println);
    }

    //spring:JpaSpecificationExecutor
    @Test
    public void test6() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Manager> criteria = builder.createQuery(Manager.class);
        Root<Manager> root = criteria.from(Manager.class);
        System.out.println(root.type());
    }

    @Test
    public void query() throws Exception {
        Session session = manager.unwrap(Session.class);
//        Query query = session.createQuery();

    }
}
