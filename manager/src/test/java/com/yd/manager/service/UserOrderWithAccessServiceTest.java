package com.yd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yd.manager.dto.record.StoreOrdersAccessDTO;
import com.yd.manager.dto.record.UserOrdersAccessDTO;
import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

public class UserOrderWithAccessServiceTest extends SpringTestInit {

    @Autowired
    private AccessAttachService service;

    @Test
    public void pageUserOrdersDTO() throws JsonProcessingException {
        Page<UserOrdersAccessDTO> page = service.pageUserOrdersAccessDTO(null, Arrays.asList(99L), new PageRequest(1, 10));
        System.out.println(mapper.writeValueAsString(page));
    }

    @Test
    public void pageStoreOrdersAccessDTO() throws JsonProcessingException {
        Page<StoreOrdersAccessDTO> page = service.pageStoreOrdersAccessDTO("", null, null);
        System.err.println(mapper.writeValueAsString(page));
    }
}