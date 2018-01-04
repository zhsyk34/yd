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

@Configuration
@ComponentScan(basePackageClasses = Entry.class)
public class SpringConfig {

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
