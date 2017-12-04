package com.yd.manager.repository.custom;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.MerchandiseOrdersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MerchandiseDTORepository {

    List<MerchandiseDTO> listMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable);

    long countMerchandiseDTO(String nameOrCode, List<Long> stores);

    Page<MerchandiseDTO> pageMerchandiseDTO(String nameOrCode, List<Long> stores, Pageable pageable);

    List<MerchandiseOrdersDTO> listMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable);

    Page<MerchandiseOrdersDTO> pageMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable);
}
