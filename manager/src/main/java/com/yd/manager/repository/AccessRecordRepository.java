package com.yd.manager.repository;

import com.yd.manager.entity.AccessRecord;
import com.yd.manager.repository.custom.AccessRecordDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRecordRepository extends JpaRepository<AccessRecord, Long>, AccessRecordDTORepository {
}
