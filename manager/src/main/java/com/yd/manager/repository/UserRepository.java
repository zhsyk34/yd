package com.yd.manager.repository;

import com.yd.manager.entity.User;
import com.yd.manager.repository.custom.UserDTORepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long>, UserDTORepository {

    int countByCreateTimeBetween(LocalDateTime begin, LocalDateTime end);

    Page<User> findByNameContains(String name, Pageable pageable);
}
