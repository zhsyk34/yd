package com.yd.ecabinet.config;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.Entry;
import com.yd.rfid.DefaultRfidOperator;
import com.yd.rfid.RfidMonitor;
import com.yd.rfid.RfidOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.yd.ecabinet.config.RfidConfig.*;
import static com.yd.ecabinet.config.StoreConfig.INTERVAL;
import static com.yd.ecabinet.util.LoggerUtils.getLogger;

@Configuration
@ComponentScan(basePackageClasses = Entry.class)
public class SpringConfig {

    @Bean
    public RfidOperator rfidOperator() {
        return DefaultRfidOperator.instance(getLogger(RfidOperator.class), ID, ANT, INTERVAL, RETRY);
    }

    @Bean
    @Autowired
    public RfidMonitor rfidMonitor(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        return RfidMonitor.instance(getLogger(RfidMonitor.class), callback, rfidOperator, INTERVAL);
    }

}
