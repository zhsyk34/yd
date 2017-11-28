package com.yd.manager.repository.custom;

import com.yd.manager.dto.Merchandise2DTO;
import com.yd.manager.dto.MerchandiseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MerchandiseDTORepository {

    List<MerchandiseDTO> findMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable);

    long countMerchandiseDTO(String name, String code, List<Long> stores);

    Page<MerchandiseDTO> pageMerchandiseDTO(String name, String code, List<Long> stores, Pageable pageable);

    List<Merchandise2DTO> findMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable);

    Page<Merchandise2DTO> pageMerchandiseDTO2(String name, String code, List<Long> stores, Pageable pageable);
}
