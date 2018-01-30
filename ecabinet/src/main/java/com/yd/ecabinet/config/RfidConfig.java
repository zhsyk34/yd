package com.yd.ecabinet.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.yd.ecabinet.config.SpringConfig.URL_PREFIX;

@Configuration
@PropertySource(URL_PREFIX + "ecabinet.properties")
@Data
public class RfidConfig {
    @Value("${rfid.id}")
    private String id;
    @Value("${rfid.ant}")
    private int ant;
    @Value("${rfid.interval}")
    private int interval;
    @Value("${rfid.scan}")
    private int scan;
}
