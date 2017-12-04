package com.yd.manager.repository;

import com.yd.manager.entity.User;
import com.yd.manager.repository.custom.UserDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserDTORepository {

}
