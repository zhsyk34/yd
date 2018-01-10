package com.yd.ecabinet.config;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.Entry;
import com.yd.rfid.DefaultRfidOperator;
import com.yd.rfid.RfidMonitor;
import com.yd.rfid.RfidOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Configuration
@ComponentScan(basePackageClasses = Entry.class)
public class SpringConfig {

    //TODO
    //    static final String URL_PREFIX = FILE_URL_PREFIX;
    static final String URL_PREFIX = CLASSPATH_URL_PREFIX;

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
        return Executors.newScheduledThreadPool(3);
    }

}
