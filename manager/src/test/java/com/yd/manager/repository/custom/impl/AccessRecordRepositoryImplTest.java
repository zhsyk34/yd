//package com.yd.manager.repository.custom.impl;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.yd.manager.dto.record.UserStoreAccessRecordDTO;
//import com.yd.manager.dto.util.DateRange;
//import com.yd.manager.repository.SpringTestInit;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class AccessRecordRepositoryImplTest extends SpringTestInit {
//
//    @Autowired
//    private AccessRecordRepositoryImpl accessRecordRepository;
//
//    @Test
//    public void countAll() {
////        System.out.println(accessRecordRepository.countAll(3L, 2L, null, Arrays.asList(2L, 3L)));
//        System.out.println(accessRecordRepository.countAll(150L, 1L, DateRange.today().toTimeRange(), Arrays.asList(1L, 12L, 13L, 14L)));
//    }
//
//    @Test
//    public void count() throws JsonProcessingException {
//        List<UserStoreAccessRecordDTO> list = accessRecordRepository.listUserStoreAccessRecordDTO(1733, null, null);
////        list.forEach(System.err::println);
//
//        System.err.println(mapper.writeValueAsString(list));
//    }
//}