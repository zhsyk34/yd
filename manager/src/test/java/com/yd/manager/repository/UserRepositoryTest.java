package com.yd.manager.repository;

import com.yd.manager.dto.TimeRange;
import com.yd.manager.dto.UserOrderCollectDTO;
import org.junit.Test;
import org.springframework.data.domain.*;

import java.time.*;
import java.util.Arrays;
import java.util.List;

public class UserRepositoryTest extends SpringTestInit {

    private LocalDate today = LocalDate.now();
    private TimeRange range = TimeRange.from(null, today);

    @Test
    public void countByCreateTimeBetween() throws Exception {
        LocalDateTime begin = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);

        System.out.println(userRepository.countByCreateTimeBetween(begin, end));
    }

    @Test
    public void findByNameContains() throws Exception {
    }

    @Test
    public void listUserOrderCollectDTO() throws Exception {
        Pageable pageable = new PageRequest(0, 10);
        List<UserOrderCollectDTO> list = userRepository.listUserOrderCollectDTO("a", null, null, pageable);
        list.forEach(System.err::println);
    }

    @Test
    public void countUserOrderCollectDTO() throws Exception {
        System.out.println(userRepository.countUserOrderCollectDTO("a", null));//71
    }

    @Test
    public void pageUserOrderCollectDTO() throws Exception {
        Pageable pageable = new PageRequest(1, 8);
        List<Long> stores = Arrays.asList(13L, 14L);
        Page<UserOrderCollectDTO> page = userRepository.pageUserOrderCollectDTO("a", range, null, pageable);
        System.out.println(mapper.writeValueAsString(page));
    }
}