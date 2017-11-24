package com.yd.manager.repository.custom;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseDTO2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MerchandiseDTORepository {

    List<MerchandiseDTO> findMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable);

    long countMerchandiseDTO(String name, String code, List<Long> stores);

    Page<MerchandiseDTO> pageMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable);

    List<MerchandiseDTO2> findMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable);

    Page<MerchandiseDTO2> pageMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable);
}
