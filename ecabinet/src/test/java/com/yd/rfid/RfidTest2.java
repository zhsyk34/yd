package com.yd.rfid;

import com.yd.ecabinet.rfid.DefaultRfidOperator;
import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.util.BeanFactory;
import com.yd.ecabinet.util.ThreadUtils;

public class RfidTest2 {

    public static void main(String[] args) {
        RfidService rfidService = BeanFactory.getBean(RfidService.class);

        rfidService.startup();

        DefaultRfidOperator rfidOperator = BeanFactory.getBean(DefaultRfidOperator.class);

        while (true) {
            rfidOperator.openDoor();
            ThreadUtils.await(1000);

//            rfidOperator.closeDoor();
//            ThreadUtils.await(500);

            rfidOperator.startRead();
            ThreadUtils.await(1000);

            rfidOperator.stopRead();
            ThreadUtils.await(1000);
        }
    }
}
