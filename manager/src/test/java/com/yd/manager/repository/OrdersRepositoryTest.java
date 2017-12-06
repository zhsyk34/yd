//package com.yd.manager.repository;
//
//import com.yd.manager.dto.StoreOrdersDTO;
//import com.yd.manager.dto.UserOrdersDTO;
//import org.junit.Test;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//
//public class OrdersRepositoryTest extends SpringTestInit {
//    private LocalDate today = LocalDate.of(2017, 11, 15);
//    private LocalDateTime begin = LocalDateTime.of(today.minusDays(7), LocalTime.MIN);
//    private LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);
//
//    @Test
//    public void findUserOrderDTO() throws Exception {
//        List<UserOrdersDTO> list = ordersRepository.listUserOrderDTO(790L, null, Arrays.asList(13L, 14L), null);
//        list.forEach(System.err::println);
//    }
//
//    @Test
//    public void findOrdersCollectDTO() throws Exception {
//        Pageable pageable = new PageRequest(0, 10, Direction.DESC, "count");
//        List<Long> stores = Arrays.asList(13L, 14L, 10L, 9L, 11L, 4L);
//        List<StoreOrdersDTO> list = ordersRepository.listOrdersCollectDTO(end, null, pageable);
//        list.forEach(System.err::println);
//    }
//
//    @Test
//    public void findOrdersCollectDTO2() throws Exception {
//        List<Long> stores = Arrays.asList(13L, 14L, 10L, 9L);
//        System.out.println(ordersRepository.getOrdersCollectDTO(begin, end, stores));
//    }
//
//}