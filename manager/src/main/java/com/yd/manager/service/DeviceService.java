package com.yd.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.manager.dto.device.DeviceResult;
import com.yd.manager.dto.device.Parameter;
import com.yd.manager.dto.device.StoreDevice;
import com.yd.manager.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    public static final String SUPER_ACCOUNT = "estore";

    private static final String BASE_URL = "http://data.estore.ai:18081/openapi/";

    private static final String SCREENSHOT = "preview/get_screenshot";

    private static final String VIDEO = "preview/html5";

    private static final String DEVICE_LIST = "device/list";

    private static final String ACCOUNT_LIST = "account/list";

    private static final Collection<Header> HEADERS = Arrays.asList(
            new BasicHeader("Content-Type", "application/json"),
            new BasicHeader("Accept", "application/json"),
            new BasicHeader("Authorization", "f0becc06-e3c9-11e7-b696-00163e08a87b")
    );

    private final ObjectMapper mapper;

    public String getScreenshot(String account, String deviceSerial, int channelNo) {
        return this.post(SCREENSHOT, Parameter.builder().account(account).deviceSerial(deviceSerial).channelNo(channelNo).build());
    }

    public String getVideo(String deviceSerial, int channelNo) {
        return this.post(VIDEO, Parameter.builder().deviceSerial(deviceSerial).channelNo(channelNo).build());
    }

    private DeviceResult getDeviceList(String account, int pageNumber, int pageSize) {
        String r = this.post(DEVICE_LIST, Parameter.builder().account(account).pageNumber(pageNumber).pageSize(pageSize).build());
        try {
            return mapper.readValue(r, DeviceResult.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<StoreDevice> listStoreDevice(String account) {
        return Optional.ofNullable(this.getDeviceList(account, 1, Integer.MAX_VALUE))
                .map(DeviceResult::getList)
                .orElse(null);
    }

    public StoreDevice getStoreDeviceByName(String account, String name) {
        return Optional.ofNullable(this.getDeviceList(account, 1, Integer.MAX_VALUE))
                .map(DeviceResult::getList)
                .map(sd -> sd.stream().collect(toMap(StoreDevice::getStoreName, identity())))
                .map(m -> m.get(name))
                .orElse(null);
    }

    public String getAccountList(int pageNumber, int pageSize) {
        return this.post(ACCOUNT_LIST, Parameter.builder().pageNumber(pageNumber).pageSize(pageSize).build());
    }

    private String post(String uri, Parameter params) {
        try {
            String json = mapper.writeValueAsString(params);
            String result = HttpUtils.post(BASE_URL + uri, HEADERS, json);
            logger.info("uri:{}\nparams:{}\nresult:{}\n------------------------", uri, json, result);

            return result;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
