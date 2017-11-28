package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserOrder2DTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDTORepository {

    List<UserOrder2DTO> findUserOrder2DTO(String name, String phone, List<Long> stores, Pageable pageable);
}
