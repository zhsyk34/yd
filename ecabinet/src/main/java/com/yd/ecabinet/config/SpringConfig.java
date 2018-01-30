package com.yd.ecabinet.config;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.Entry;
import com.yd.rfid.DefaultRfidOperator;
import com.yd.rfid.RfidMonitor;
import com.yd.rfid.RfidOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.util.ResourceUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@ComponentScan(basePackageClasses = Entry.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.yd.ecabinet.server.script.*")})
public class SpringConfig {
    static final String URL_PREFIX = ResourceUtils.FILE_URL_PREFIX;
//    static final String URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

    @Bean
    public RfidOperator rfidOperator(RfidConfig rfidConfig) {
        return DefaultRfidOperator.instance(rfidConfig.getId(), rfidConfig.getAnt());
    }

    @Bean
    public RfidMonitor rfidMonitor(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        return RfidMonitor.instance(callback, rfidOperator);
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(5);
    }
}
