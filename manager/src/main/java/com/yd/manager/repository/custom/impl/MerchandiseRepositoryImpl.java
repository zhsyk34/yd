package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.Merchandise2DTO;
import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.custom.MerchandiseDTORepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.yd.manager.utils.JpaUtils.from;
import static com.yd.manager.utils.JpaUtils.matchString;

@Repository
public class MerchandiseRepositoryImpl implements MerchandiseDTORepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<MerchandiseDTO> findMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<MerchandiseDTO> criteria = builder.createQuery(MerchandiseDTO.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        criteria.multiselect(
                merchandiseJoin.get(Merchandise_.id),
                merchandiseJoin.get(Merchandise_.name),
                merchandiseJoin.get(Merchandise_.code),
                merchandiseJoin.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                specificationRoot.get(MerchandiseSpecification_.no),
                specificationRoot.get(MerchandiseSpecification_.name),
                specificationRoot.get(MerchandiseSpecification_.price)
        );

        List<Predicate> predicates = predicates(builder, merchandiseJoin, storeJoin, name, code, stores);
        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        if (pageable != null) {
            criteria.orderBy(from(builder, merchandiseJoin, pageable.getSort()));
        }

        TypedQuery<MerchandiseDTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    @Override
    public long countMerchandiseDTO(String name, String code, List<Long> stores) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        List<Predicate> predicates = predicates(builder, merchandiseJoin, storeJoin, name, code, stores);
        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        criteria.select(builder.count(specificationRoot));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<MerchandiseDTO> pageMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.findMerchandiseDTO(name, code, stores, pageable), pageable, this.countMerchandiseDTO(name, code, stores));
    }

    @Override
    public List<Merchandise2DTO> findMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Merchandise2DTO> criteria = builder.createQuery(Merchandise2DTO.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        //sub query
        Subquery<Long> subQuery = criteria.subquery(Long.class);
        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
        Join<OrdersMerchandise, MerchandiseSpecification> specificationJoin = ordersMerchandiseRoot.join(OrdersMerchandise_.specification);

        //OrdersMerchandise om
        //inner join MerchandiseSpecification ms
        //on om.specification = ms
        //where ms = (outer) MerchandiseSpecification

        subQuery.where(builder.equal(specificationJoin, specificationRoot));
        //the same as
        //builder.equal(specificationJoin.get(MerchandiseSpecification_.id), specificationRoot.get(MerchandiseSpecification_.id));

        subQuery.select(builder.count(ordersMerchandiseRoot));

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

        List<Predicate> predicates = predicates(builder, merchandiseJoin, storeJoin, name, code, stores);
        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        if (pageable != null) {
            criteria.orderBy(from(builder, merchandiseJoin, pageable.getSort()));
        }

        TypedQuery<Merchandise2DTO> query = manager.createQuery(criteria);

        if (pageable != null) {
            query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    @Override
    public Page<Merchandise2DTO> pageMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.findMerchandiseDTO2(name, code, stores, pageable), pageable, this.countMerchandiseDTO(name, code, stores));
    }

    private List<Predicate> predicates(CriteriaBuilder builder, Join<MerchandiseStore, Merchandise> merchandiseJoin, Join<MerchandiseStore, Store> storeJoin, String name, String code, List<Long> stores) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(name)) {
            predicates.add(builder.like(merchandiseJoin.get(Merchandise_.name), matchString(name)));
        }
        if (StringUtils.hasText(code)) {
            predicates.add(builder.like(merchandiseJoin.get(Merchandise_.code), matchString(code)));
        }
        if (!CollectionUtils.isEmpty(stores)) {
            predicates.add(storeJoin.get(Store_.id).in(stores));
        }
        return predicates;
    }
}
