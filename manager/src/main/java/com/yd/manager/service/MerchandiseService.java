package com.yd.manager.service;

import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.repository.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MerchandiseService {

    private final MerchandiseRepository merchandiseRepository;

    public Page<MerchandiseOrdersDTO> pageMerchandiseOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return merchandiseRepository.pageMerchandiseOrdersDTO(nameOrCode, stores, pageable);
    }
}
