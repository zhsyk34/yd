package com.yd.manager.repository;

import com.yd.manager.entity.Orders;
import com.yd.manager.repository.custom.OrdersDTORepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersDTORepository {

}
