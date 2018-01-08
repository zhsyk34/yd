package com.yd.manager.repository;

import com.yd.manager.entity.Store;
import com.yd.manager.repository.custom.StoreDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreDTORepository {

    List<Store> findByIdIn(Collection<Long> stores);
}
