package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDTORepository {

    List<UserDTO> findUserDTO(String name, String phone, List<Long> stores, Pageable pageable);
}
