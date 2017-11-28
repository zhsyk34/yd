package com.yd.manager.repository;

import com.yd.manager.dto.UserDTO;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.*;
import java.util.List;

public class UserRepositoryTest extends SpringTestInit {
    @Test
    public void countByCreateTimeBetween() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDateTime begin = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);

        System.out.println(begin);
        System.out.println(end);
        System.out.println(userRepository.countByCreateTimeBetween(begin, end));
    }

    @Test
    public void findByNameContains() throws Exception {
    }

    @Test
    public void findUserDTO() throws Exception {
        Pageable pageable = new PageRequest(0, 10);
        List<UserDTO> list = userRepository.findUserDTO(null, null, null, pageable);
        list.forEach(System.err::println);
    }
}