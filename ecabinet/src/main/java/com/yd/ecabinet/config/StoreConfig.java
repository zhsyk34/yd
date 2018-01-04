package com.yd.ecabinet.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Configuration
@PropertySource(CLASSPATH_URL_PREFIX + "ecabinet.properties")
@Data
public class StoreConfig {
    @Value("${store.name}")
    private String name;
    @Value("${store.number}")
    private String number;
    @Value("${store.sync}")
    private int sync;
}
