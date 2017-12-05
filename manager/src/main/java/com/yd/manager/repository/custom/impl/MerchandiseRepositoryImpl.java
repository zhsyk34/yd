package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.MerchandiseDTORepository;
import com.yd.manager.utils.jpa.JpaUtils;
import com.yd.manager.utils.jpa.PredicateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@Repository
public class MerchandiseRepositoryImpl implements MerchandiseDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<MerchandiseDTO> listMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<MerchandiseDTO> criteria = builder.createQuery(MerchandiseDTO.class);

        //from
        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        //where
        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandiseJoin, nameOrCode, storeJoin, stores));

        //order by
        criteria.orderBy(builder.desc(merchandiseJoin.get(Merchandise_.id)));

        //select
        criteria.multiselect(
                merchandiseJoin.get(Merchandise_.id),
                merchandiseJoin.get(Merchandise_.name),
                merchandiseJoin.get(Merchandise_.code),
                merchandiseJoin.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                specificationRoot.get(MerchandiseSpecification_.specId),
                specificationRoot.get(MerchandiseSpecification_.name),
                specificationRoot.get(MerchandiseSpecification_.price)
        );

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public long countMerchandiseDTO(String nameOrCode, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandiseJoin, nameOrCode, storeJoin, stores));

        criteria.select(builder.count(specificationRoot));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<MerchandiseDTO> pageMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listMerchandiseDTO(nameOrCode, stores, pageable), pageable, this.countMerchandiseDTO(nameOrCode, stores));
    }

//    @Override
//    public List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<MerchandiseOrdersDTO> criteria = builder.createQuery(MerchandiseOrdersDTO.class);
//
//        //from
//        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
//        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
//        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
//        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);
//
//        //where
//        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandiseJoin, nameOrCode, storeJoin, stores));
//
//        //sub query
//        Subquery<Long> subQuery = criteria.subquery(Long.class);
//        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
//        Join<OrdersMerchandise, MerchandiseSpecification> specificationJoin = ordersMerchandiseRoot.join(OrdersMerchandise_.specification);
//
//        //OrdersMerchandise om
//        //inner join MerchandiseSpecification ms
//        //on om.specification = ms
//        //where ms = (outer) MerchandiseSpecification
//
//        //the same as
//        //builder.equal(specificationJoin.get(MerchandiseSpecification_.id), specificationRoot.get(MerchandiseSpecification_.id));
//        subQuery.where(builder.equal(specificationJoin, specificationRoot));
//
//        subQuery.select(builder.count(ordersMerchandiseRoot));
//
//        //select
//        criteria.multiselect(
//                merchandiseJoin.get(Merchandise_.id),
//                merchandiseJoin.get(Merchandise_.name),
//                merchandiseJoin.get(Merchandise_.code),
//                merchandiseJoin.join(Merchandise_.category).get(MerchandiseCategory_.name),
//                storeJoin.get(Store_.id),
//                storeJoin.get(Store_.name),
//                specificationRoot.get(MerchandiseSpecification_.no),
//                specificationRoot.get(MerchandiseSpecification_.name),
//                specificationRoot.get(MerchandiseSpecification_.price),
//                subQuery.getSelection()
//        );
//
//        //order by
//        criteria.orderBy(builder.desc(merchandiseJoin.get(Merchandise_.id)));
//        //criteria.orderBy(builder.desc(subQuery));//TODO
//
//        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
//    }

    @Override
    public List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<MerchandiseOrdersDTO> criteria = builder.createQuery(MerchandiseOrdersDTO.class);

        //from
        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);
//        specificationRoot.join(MerchandiseSpecification_.)

        //estore_order_goods.inventory_id = estore_inventory_spec.id ?
        //where
        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandiseJoin, nameOrCode, storeJoin, stores));

        //sub query
        Subquery<Long> subQuery = criteria.subquery(Long.class);
        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
        Join<OrdersMerchandise, MerchandiseSpecification> specificationJoin = ordersMerchandiseRoot.join(OrdersMerchandise_.specification);

        //OrdersMerchandise om
        //inner join MerchandiseSpecification ms
        //on om.specification = ms
        //where ms = (outer) MerchandiseSpecification

        //the same as
        //builder.equal(specificationJoin.get(MerchandiseSpecification_.id), specificationRoot.get(MerchandiseSpecification_.id));
        subQuery.where(builder.equal(specificationJoin, specificationRoot));

        subQuery.select(builder.count(ordersMerchandiseRoot));

        //select
        criteria.multiselect(
                merchandiseJoin.get(Merchandise_.id),
                merchandiseJoin.get(Merchandise_.name),
                merchandiseJoin.get(Merchandise_.code),
                merchandiseJoin.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                specificationRoot.get(MerchandiseSpecification_.no),
                specificationRoot.get(MerchandiseSpecification_.name),
                specificationRoot.get(MerchandiseSpecification_.price),
                subQuery.getSelection()
        );

        //order by
        criteria.orderBy(builder.desc(merchandiseJoin.get(Merchandise_.id)));
        //criteria.orderBy(builder.desc(subQuery));//TODO

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public Page<MerchandiseOrdersDTO> pageMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listMerchandiseOrdersDTO(nameOrCode, stores, pageable), pageable, this.countMerchandiseDTO(nameOrCode, stores));
    }

    private Predicate restrictForMerchandise(CriteriaBuilder builder, Path<Merchandise> path, String nameOrCode) {
        if (StringUtils.hasText(nameOrCode)) {
            Predicate likeName = builder.like(path.get(Merchandise_.name), JpaUtils.matchString(nameOrCode));
            Predicate likeCode = builder.like(path.get(Merchandise_.code), JpaUtils.matchString(nameOrCode));
            return builder.or(likeName, likeCode);
        }
        return null;
    }

    private Predicate restrictForStore(Path<Store> path, List<Long> stores) {
        return CollectionUtils.isEmpty(stores) ? null : path.get(Store_.id).in(stores);
    }

    private Collection<Predicate> restrict(CriteriaBuilder builder, Path<Merchandise> merchandisePath, String nameOrCode, Path<Store> storePath, List<Long> stores) {
        return PredicateFactory.instance()
                .append(this.restrictForMerchandise(builder, merchandisePath, nameOrCode))
                .append(this.restrictForStore(storePath, stores))
                .get();
    }
}
