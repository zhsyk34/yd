package com.yd.manager.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.manager.util.HttpUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@Slf4j
public class DeviceService {

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
    @Autowired
    private ObjectMapper mapper;

    public String getScreenshot(String account, String deviceSerial, int channelNo) {
        return this.post(SCREENSHOT, Parameter.builder().account(account).deviceSerial(deviceSerial).channelNo(channelNo).build());
    }

    public String getVideo(String deviceSerial, int channelNo) {
        return this.post(VIDEO, Parameter.builder().deviceSerial(deviceSerial).channelNo(channelNo).build());
    }

    public String getDeviceList(String account, int pageNumber, int pageSize) {
        return this.post(DEVICE_LIST, Parameter.builder().account(account).pageNumber(pageNumber).pageSize(pageSize).build());
    }

    public String getAccountList(int pageNumber, int pageSize) {
        return this.post(ACCOUNT_LIST, Parameter.builder().pageNumber(pageNumber).pageSize(pageSize).build());
    }

    //TODO
    private String post(String uri, Parameter params) {
        try {
            String json = mapper.writeValueAsString(params);
            String r = HttpUtils.postJson(BASE_URL + uri, HEADERS, json);
            logger.info("uri:{}\nparams:{}\nresult:{}\n------------------------", uri, json, r);

            return r;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Parameter {
        @JsonProperty("account")
        private String account;
        @JsonProperty("device_serial")
        private String deviceSerial;
        @JsonProperty("chan_no")
        private Integer channelNo;
        @JsonProperty("page_num")
        private Integer pageNumber;
        @JsonProperty("page_size")
        private Integer pageSize;
    }

}
