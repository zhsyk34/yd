package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserDTO;

import java.util.List;

public interface UserDTORepository {

    List<UserDTO> findUserDTO(String name, String phone, List<Long> stores);
}
