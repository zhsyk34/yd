package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.rfid.executor.AbstractDaemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RfidService extends AbstractDaemonService {

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    @Autowired
    public RfidService(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        this.callback = callback;
        this.rfidOperator = rfidOperator;
    }

    @Override
    public void run() {
        rfidOperator.connect(callback);
        super.setStartup(true);
    }

}
