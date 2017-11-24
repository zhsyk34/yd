package com.yd.manager.controller;

import com.yd.manager.dto.MerchandiseDTO;
import com.yd.manager.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchandise")
@Slf4j
public class MerchandiseController extends CommonController {

    @GetMapping
    public Result<Page<MerchandiseDTO>> retrieve(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String code, @PageableDefault(size = 15, sort = "id", direction = Direction.DESC) Pageable pageable) {
        logger.info("retrieve merchandise by name:{} and code:{} with page:{}", name, code, pageable);
        return null;
//        return Result.success(merchandiseRepository.findDTO(name, code, pageable));
    }
}
