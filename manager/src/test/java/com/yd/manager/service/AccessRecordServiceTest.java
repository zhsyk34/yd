package com.yd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yd.manager.dto.AccessRecordDTO;
import com.yd.manager.dto.StoreAccessRecordDTO;
import com.yd.manager.dto.UserAccessRecordDTO;
import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class AccessRecordServiceTest extends SpringTestInit {

    @Autowired
    private AccessRecordService accessRecordService;

    @Test
    public void getAccessRecordDTO() throws JsonProcessingException {
        AccessRecordDTO dto = accessRecordService.getAccessRecordDTO(null, null);
        System.err.println(mapper.writeValueAsString(dto));
    }

    @Test
    public void getUserAccessRecordDTO() throws JsonProcessingException {
        AccessRecordDTO dto = accessRecordService.getUserAccessRecordDTO(1733, null, null);
        System.err.println(mapper.writeValueAsString(dto));
    }

    @Test
    public void listUserAccessRecordDTO() throws JsonProcessingException {
        List<UserAccessRecordDTO> dto = accessRecordService.listUserAccessRecordDTO(Arrays.asList(1733L, 6838L), null, Arrays.asList(1L, 9L));
        System.err.println(mapper.writeValueAsString(dto));
    }

    @Test
    public void getStoreAccessRecordDTO() throws JsonProcessingException {
        AccessRecordDTO dto = accessRecordService.getStoreAccessRecordDTO(1L, null);
        System.err.println(mapper.writeValueAsString(dto));

    }

    @Test
    public void listStoreAccessRecordDTO() throws JsonProcessingException {
//        List<StoreAccessRecordDTO> dto = accessRecordService.listStoreAccessRecordDTO(null, Arrays.asList(1L, 9L));
        List<StoreAccessRecordDTO> dto = accessRecordService.listStoreAccessRecordDTO(null, null);
        System.err.println(mapper.writeValueAsString(dto));
    }

    @Test
    public void listUserStoreAccessRecordDTO() throws JsonProcessingException {
//        List<?> dto = accessRecordService.listUserStoreAccessRecordDTO(1733L, null, Arrays.asList(1L, 9L));
        List<?> dto = accessRecordService.listUserStoreAccessRecordDTO(1733L, null, null);
        System.err.println(mapper.writeValueAsString(dto));
    }
}