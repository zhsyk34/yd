package com.yd.manager.repository;

import com.yd.manager.entity.Merchandise;
import com.yd.manager.repository.custom.MerchandiseDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long>, MerchandiseDTORepository {

}
