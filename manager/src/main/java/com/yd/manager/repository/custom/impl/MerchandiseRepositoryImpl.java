package com.yd.manager.repository.custom.impl;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.entity.*;
import com.yd.manager.repository.RestrictUtils;
import com.yd.manager.repository.custom.MerchandiseDTORepository;
import com.yd.manager.util.jpa.JpaUtils;
import com.yd.manager.util.jpa.PredicateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@Repository
public class MerchandiseRepositoryImpl implements MerchandiseDTORepository {
    private final EntityManager manager;
    private final CriteriaBuilder builder;

    public MerchandiseRepositoryImpl(EntityManager manager) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
    }

    @Override
    public List<MerchandiseDTO> listMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        CriteriaQuery<MerchandiseDTO> criteria = builder.createQuery(MerchandiseDTO.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        JpaUtils.setPredicate(criteria, this.restrict(specificationRoot, merchandiseJoin, nameOrCode, storeJoin, stores));

        criteria.multiselect(
                merchandiseJoin.get(Merchandise_.id),
                merchandiseJoin.get(Merchandise_.name),
                merchandiseJoin.get(Merchandise_.code),
                merchandiseJoin.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storeJoin.get(Store_.id),
                storeJoin.get(Store_.name),
                specificationRoot.get(MerchandiseSpecification_.specCode),
                specificationRoot.get(MerchandiseSpecification_.name),
                specificationRoot.get(MerchandiseSpecification_.price)
        );

        criteria.orderBy(builder.desc(merchandiseJoin.get(Merchandise_.id)));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public long countMerchandiseDTO(String nameOrCode, List<Long> stores) {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<MerchandiseSpecification> specificationRoot = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStoreJoin = specificationRoot.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandiseJoin = merchandiseStoreJoin.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storeJoin = merchandiseStoreJoin.join(MerchandiseStore_.store);

        JpaUtils.setPredicate(criteria, this.restrict(specificationRoot, merchandiseJoin, nameOrCode, storeJoin, stores));

        criteria.select(builder.count(specificationRoot));

        return manager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Page<MerchandiseDTO> pageMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listMerchandiseDTO(nameOrCode, stores, pageable), pageable, this.countMerchandiseDTO(nameOrCode, stores));
    }

    @Override
    public List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        CriteriaQuery<MerchandiseOrdersDTO> criteria = builder.createQuery(MerchandiseOrdersDTO.class);

        Root<MerchandiseSpecification> specificationPath = criteria.from(MerchandiseSpecification.class);
        Join<MerchandiseSpecification, MerchandiseStore> merchandiseStorePath = specificationPath.join(MerchandiseSpecification_.MerchandiseStore);
        Join<MerchandiseStore, Merchandise> merchandisePath = merchandiseStorePath.join(MerchandiseStore_.merchandise);
        Join<MerchandiseStore, Store> storePath = merchandiseStorePath.join(MerchandiseStore_.store);

        JpaUtils.setPredicate(criteria, this.restrict(specificationPath, merchandisePath, nameOrCode, storePath, stores));

        Subquery<Integer> subQuery = criteria.subquery(Integer.class);
        Root<OrdersMerchandise> ordersMerchandiseRoot = subQuery.from(OrdersMerchandise.class);
        Path<MerchandiseStore> merchandiseStore = ordersMerchandiseRoot.join(OrdersMerchandise_.merchandiseStore);

        Collection<Predicate> predicates = PredicateBuilder.init(RestrictUtils.restrictForOrders(builder, ordersMerchandiseRoot.join(OrdersMerchandise_.orders)))
                .append(builder.equal(merchandiseStore.get(MerchandiseStore_.store).get(Store_.id), storePath.get(Store_.id)))
                .append(builder.equal(merchandiseStore, merchandiseStorePath)).build();
        JpaUtils.setPredicate(subQuery, predicates);

        subQuery.select(builder.sum(ordersMerchandiseRoot.get(OrdersMerchandise_.count)));

        criteria.multiselect(
                merchandisePath.get(Merchandise_.id),
                merchandisePath.get(Merchandise_.name),
                merchandisePath.get(Merchandise_.code),
                merchandisePath.join(Merchandise_.category).get(MerchandiseCategory_.name),
                storePath.get(Store_.id),
                storePath.get(Store_.name),
                specificationPath.get(MerchandiseSpecification_.specCode),
                specificationPath.get(MerchandiseSpecification_.name),
                specificationPath.get(MerchandiseSpecification_.price),
                subQuery.getSelection()
        );

        criteria.orderBy(builder.desc(merchandisePath.get(Merchandise_.id)));

        return JpaUtils.getResultListByPageable(manager, criteria, pageable);
    }

    @Override
    public Page<MerchandiseOrdersDTO> pageMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return new PageImpl<>(this.listMerchandiseOrdersDTO(nameOrCode, stores, pageable), pageable, this.countMerchandiseDTO(nameOrCode, stores));
    }

    private Predicate restrictForMerchandiseSpecification(Path<MerchandiseSpecification> path) {
        return builder.equal(path.get(MerchandiseSpecification_.source), 2);
    }

    private Predicate restrictForMerchandise(Path<Merchandise> path, String nameOrCode) {
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

    private Collection<Predicate> restrict(Root<MerchandiseSpecification> specificationPath, Path<Merchandise> merchandisePath, String nameOrCode, Path<Store> storePath, List<Long> stores) {
        return PredicateBuilder.instance()
                .append(this.restrictForMerchandiseSpecification(specificationPath))
                .append(this.restrictForMerchandise(merchandisePath, nameOrCode))
                .append(this.restrictForStore(storePath, stores))
                .build();
    }
}
