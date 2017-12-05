package com.yd.manager.repository;

import com.yd.manager.entity.Store;
import com.yd.manager.repository.custom.StoreDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreDTORepository {
}
