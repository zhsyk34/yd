package com.yd.manager.repository;

import com.yd.manager.entity.Manager;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findByLoginName(@NonNull String loginName);

}
