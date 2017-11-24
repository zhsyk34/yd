package com.yd.manager.repository;

import org.junit.Test;

import java.time.*;

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
        userRepository.findUserDTO(null, null, null);
    }
}