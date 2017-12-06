package com.yd.manager.repository.custom;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public class MerchandiseDTORepositoryTest extends SpringTestInit {

    private List<Long> stores = Arrays.asList(13L, 9L);
    private Pageable pageable = new PageRequest(1, 3);

    @Test
    public void listMerchandiseDTO() {

    }

    @Test
    public void countMerchandiseDTO() {
    }

    @Test
    public void pageMerchandiseDTO() {
        Page<MerchandiseDTO> page = merchandiseRepository.pageMerchandiseDTO(null, stores, pageable);
        page.getContent().forEach(System.err::println);
        System.err.println(page.getTotalPages());
    }

    @Test
    public void listMerchandiseOrdersDTO() {
    }

    @Test
    public void pageMerchandiseOrdersDTO() {
        Page<MerchandiseOrdersDTO> page = merchandiseRepository.pageMerchandiseOrdersDTO("水果", stores, null);
        page.getContent().forEach(System.err::println);
        System.err.println(page.getTotalPages());
    }
}