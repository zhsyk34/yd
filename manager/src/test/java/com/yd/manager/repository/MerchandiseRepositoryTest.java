package com.yd.manager.repository;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseDTO2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MerchandiseRepositoryTest extends SpringTestInit {

    @Test
    public void findMerchandiseDTO() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 15, Direction.DESC, "id");
        List<MerchandiseDTO> list = merchandiseRepository.findMerchandiseDTO("雪碧", null, Arrays.asList(11L, 13L), pageRequest);
        list.forEach(System.err::println);
    }

    @Test
    public void countMerchandiseDTO() throws Exception {
        System.err.println(merchandiseRepository.countMerchandiseDTO("雪碧", null, Arrays.asList(11L, 13L)));

        List<MerchandiseDTO> list = merchandiseRepository.findMerchandiseDTO("雪碧", null, Arrays.asList(11L, 13L), null);
        System.err.println(list.size());
    }

    @Test
    public void pageMerchandiseDTO() throws Exception {
        System.err.println(merchandiseRepository.findMerchandiseDTO("雪碧", null, null, null).size());

        PageRequest pageRequest = new PageRequest(1, 7, Direction.DESC, "id");
        Page<MerchandiseDTO> page = merchandiseRepository.pageMerchandiseDTO("雪碧", null, null, pageRequest);
        System.out.println(mapper.writeValueAsString(page));
    }

    @Test
    public void findMerchandiseDTO2() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 25, Direction.DESC, "id");
        List<MerchandiseDTO2> list = merchandiseRepository.findMerchandiseDTO2("雪碧", null, null, pageRequest);
        list.forEach(System.err::println);
    }

}