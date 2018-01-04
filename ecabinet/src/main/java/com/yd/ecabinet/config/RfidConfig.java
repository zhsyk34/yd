package com.yd.ecabinet.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Configuration
@PropertySource(CLASSPATH_URL_PREFIX + "ecabinet.properties")
@Data
public class RfidConfig {
    @Value("${rfid.id}")
    private String id;
    @Value("${rfid.ant}")
    private int ant;
}
