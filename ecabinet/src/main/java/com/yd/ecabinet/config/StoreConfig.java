package com.yd.ecabinet.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.yd.ecabinet.config.SpringConfig.URL_PREFIX;

@Configuration
@PropertySource(URL_PREFIX + "ecabinet.properties")
@Data
public class StoreConfig {
    @Value("${store.name}")
    private String name;
    @Value("${store.number}")
    private String number;
    @Value("${store.sync}")
    private int sync;//同步rfid与服务器状态频率

    @Value("${store.orderUrl}")
    private String orderUrl;

    @Value("${store.signalUrl}")
    private String signalUrl;
    @Value("${store.signalInterval}")
    private int signalInterval;

    @Value("${store.scriptUrl}")
    private String scriptUrl;
}
