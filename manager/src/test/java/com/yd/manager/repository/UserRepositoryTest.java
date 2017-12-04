package com.yd.manager.repository;

import com.yd.manager.dto.UserOrdersCollectByDateDTO;
import com.yd.manager.dto.UserOrdersCollectDTO;
import com.yd.manager.dto.UserStoreOrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.utils.TimeUtils;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest extends SpringTestInit {

    private LocalDate today = LocalDate.now();
    private TimeRange range = DateRange.of(null, today).toTimeRange();

    @Test
    public void countByCreateTimeBetween() throws Exception {
        LocalDateTime begin = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);

        System.out.println(userRepository.countByCreateTime(TimeRange.of(begin, end), null));
    }

    @Test
    public void findByNameContains() throws Exception {
    }

    @Test
    public void listUserOrderCollectDTO() throws Exception {
        Pageable pageable = new PageRequest(0, 100);
        List<UserOrdersCollectDTO> list = userRepository.listUserOrderCollectDTO("", null, null, null);
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
        Page<UserOrdersCollectDTO> page = userRepository.pageUserOrderCollectDTO("a", range, null, pageable);
        System.out.println(mapper.writeValueAsString(page));
    }

    //id=115, name=上官, ordersCount=70, ordersMoney=304.99, ordersAverage=6.630217

    /**
     * UserStoreOrdersDTO(id=115, name=上官, storeId=9, storeName=观音山旗舰店, ordersCount=49, ordersMoney=222.03, ordersAverage=8.8812)
     * UserStoreOrdersDTO(id=115, name=上官, storeId=10, storeName=无锡旗舰店, ordersCount=1, ordersMoney=5.10, ordersAverage=5.1)
     * UserStoreOrdersDTO(id=115, name=上官, storeId=11, storeName=会展店, ordersCount=11, ordersMoney=60.81, ordersAverage=5.528182)
     * UserStoreOrdersDTO(id=115, name=上官, storeId=12, storeName=测试云柜, ordersCount=7, ordersMoney=12.05, ordersAverage=1.721429)
     * UserStoreOrdersDTO(id=115, name=上官, storeId=13, storeName=湖里区地丰公寓店, ordersCount=2, ordersMoney=5.00, ordersAverage=2.5)
     */
    @Test
    public void listUserStoreOrdersDTO() throws Exception {
        List<UserStoreOrdersDTO> list = userRepository.listUserStoreOrdersDTO(115, null, null);
        list.forEach(System.err::println);

        System.out.println(list.stream().map(UserStoreOrdersDTO::getOrdersMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Test
    public void getUserOrderCollectByDateDTO() throws Exception {
        LocalDate base = LocalDate.of(2017, 8, 1);
        for (int i = 0; i < 100; i++) {
            LocalDate day = base.plusDays(i);
            UserOrdersCollectByDateDTO dto = userRepository.getUserOrderCollectByDateDTO(115L, day, null);
            Optional.ofNullable(dto).ifPresent(System.err::println);
        }
    }

    @Test
    public void name() throws Exception {
        System.out.println(TimeUtils.parseSecond(1501758206));
    }

    @Test
    public void countByTime() {
        System.out.println(userRepository.countByCreateTime(DateRange.month().toTimeRange(), null));
    }
}