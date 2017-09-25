package com.yd.estore.config;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.estore.Entry;
import com.yd.rfid.DefaultRfidOperator;
import com.yd.rfid.RfidMonitor;
import com.yd.rfid.RfidOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.yd.estore.config.RfidConfig.*;
import static com.yd.estore.config.StoreConfig.STORE_INTERVAL;
import static com.yd.estore.util.LoggerUtils.getLogger;

@Configuration
@ComponentScan(basePackageClasses = Entry.class)
public class SpringConfig {

    @Bean
    public RfidOperator rfidOperator() {
        return DefaultRfidOperator.instance(getLogger(RfidOperator.class), RFID_ID, RFID_ANT, STORE_INTERVAL, RFID_RETRY);
    }

    @Bean
    @Autowired
    public RfidMonitor rfidMonitor(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        return RfidMonitor.instance(getLogger(RfidMonitor.class), callback, rfidOperator, STORE_INTERVAL);
    }

}
