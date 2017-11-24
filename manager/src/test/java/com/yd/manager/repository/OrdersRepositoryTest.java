//package com.yd.manager.repository;
//
//import org.junit.Test;
//
//import java.time.*;
//
//public class OrdersRepositoryTest extends SpringTestInit {
//
//    @Test
//    public void findUserOrderDTO() throws Exception {
//        LocalDate today = LocalDate.of(2017, 11, 14);
//        LocalDateTime begin = LocalDateTime.of(today, LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);
//
//        System.out.println(ordersRepository.findUserOrderDTO(13L, true, begin, end));
//    }
//
//    @Test
//    public void countByPaidAndFilterByTime() throws Exception {
//        LocalDate today = LocalDate.of(2017, 11, 14);
//        LocalDateTime begin = LocalDateTime.of(today, LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(today, LocalTime.MAX);
//
//        System.out.println(ordersRepository.countByPaidAndFilterByTime(true, begin, end));
//    }
//
//    @Test
//    public void countByPaid() throws Exception {
//        System.out.println(ordersRepository.countByPaid(true));
//    }
//
//}