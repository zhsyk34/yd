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

        //order by
        criteria.orderBy(builder.desc(merchandiseJoin.get(Merchandise_.id)));

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

    @Override
    public List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<MerchandiseOrdersDTO> criteria = builder.createQuery(MerchandiseOrdersDTO.class);

        //from
        Root<MerchandiseSpecification> specificationPath = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStorePath = specificationPath.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandisePath = merchandiseStorePath.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storePath = merchandiseStorePath.join(MerchandiseStore_.store);

        //where
        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandisePath, nameOrCode, storePath, stores));

        //sub query
        Subquery<Long> subQuery = criteria.subquery(Long.class);
        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);

        subQuery.where(
                builder.and(
                        builder.equal(ordersMerchandiseRoot.get(OrdersMerchandise_.specId), specificationPath.get(MerchandiseSpecification_.specId)),
                        builder.equal(ordersMerchandiseRoot.get(OrdersMerchandise_.merchandiseStore), merchandiseStorePath)
                        //builder.equal(ordersMerchandiseRoot.join(OrdersMerchandise_.merchandiseStore), merchandiseStorePath) //the same
                )
        );

        subQuery.select(builder.count(ordersMerchandiseRoot));

        //select
        criteria.multiselect(
                merchandisePath.get(Merchandise_.id),
                merchandisePath.get(Merchandise_.name),
                merchandisePath.get(Merchandise_.code),
                merchandisePath.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storePath.get(Store_.id),
                storePath.get(Store_.name),
                specificationPath.get(MerchandiseSpecification_.specId),
                specificationPath.get(MerchandiseSpecification_.name),
                specificationPath.get(MerchandiseSpecification_.price),
                subQuery.getSelection()
        );

        //order by
        criteria.orderBy(builder.desc(merchandisePath.get(Merchandise_.id)));
//        criteria.orderBy(builder.desc(subQuery.getSelection()));//TODO

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

//    @Override
//    public List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<MerchandiseOrdersDTO> criteria = builder.createQuery(MerchandiseOrdersDTO.class);
//
//        //from
//        Root<MerchandiseSpecification> specificationPath = criteria.from(MerchandiseSpecification.class);
//        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStorePath = specificationPath.join(MerchandiseSpecification_.MerchandiseStore);
//        Join<MerchandiseStore, Merchandise> merchandisePath = merchandiseStorePath.join(MerchandiseStore_.merchandise);
//        Join<MerchandiseStore, Store> storePath = merchandiseStorePath.join(MerchandiseStore_.store);
//
//        SetJoin<MerchandiseStore, OrdersMerchandise> ordersMerchandisePath = merchandiseStorePath.join(MerchandiseStore_.ordersMerchandises, LEFT);
//
//        //where
//        Predicate equal = builder.equal(specificationPath.get(MerchandiseSpecification_.specId), ordersMerchandisePath.get(OrdersMerchandise_.specId));
//        Collection<Predicate> predicates = PredicateFactory.instance()
//                .append()
//                .append(equal)
//                .get();
//
//        //left join on *** and *** != join on where ?
//        JpaUtils.setPredicate(criteria, this.restrict(builder, merchandisePath, nameOrCode, storePath, stores));
//
//        //select
//        Expression<Long> count = builder.count(ordersMerchandisePath);
//        criteria.multiselect(
//                merchandisePath.get(Merchandise_.id),
//                merchandisePath.get(Merchandise_.name),
//                merchandisePath.get(Merchandise_.code),
//                merchandisePath.join(Merchandise_.category).get(MerchandiseCategory_.name),
//                storePath.get(Store_.id),
//                storePath.get(Store_.name),
//                specificationPath.get(MerchandiseSpecification_.specId),
//                specificationPath.get(MerchandiseSpecification_.name),
//                specificationPath.get(MerchandiseSpecification_.price),
//                count
//        );
//
//        criteria.groupBy(merchandisePath, storePath, specificationPath);
//
//        //order by
//        criteria.orderBy(builder.desc(merchandisePath.get(Merchandise_.id)));
////        criteria.orderBy(builder.desc(count));//TODO
//
//        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
//    }

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
