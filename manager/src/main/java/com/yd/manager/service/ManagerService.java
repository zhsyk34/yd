package com.yd.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.manager.dto.util.ManagerInfo;
import com.yd.manager.dto.util.PhpData;
import com.yd.manager.util.HttpUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerService {

    private static final Map<Long, ManagerInfo> ID_CACHE = new ConcurrentHashMap<>();
    private static final String URL = "http://www.estore.ai/api/user/login";
    private final ObjectMapper mapper;

    public ManagerInfo getManagerInfo(String username, String password) {
        ManagerInfo managerInfo = Optional.ofNullable(this.getData(username, password)).map(PhpData::getResult).orElse(null);
        Optional.ofNullable(managerInfo).ifPresent(this::cache);
        return managerInfo;
    }

    private void cache(@NonNull ManagerInfo info) {
        ID_CACHE.put(info.getId(), info);
    }

    public ManagerInfo getManagerInfo(long id) {
        return ID_CACHE.get(id);
    }

    private PhpData getData(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return null;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);

        String response = HttpUtils.get(URL, param);
        logger.info("user login[{}:{}]", username, password);
        logger.info("php response:{}", response);

        if (!StringUtils.hasText(response)) {
            return null;
        }

        try {
            return mapper.readValue(response, PhpData.class);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
