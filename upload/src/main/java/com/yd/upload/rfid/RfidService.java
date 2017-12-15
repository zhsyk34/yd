package com.yd.upload.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.upload.config.StoreConfig.INTERVAL;

@Service
public class RfidService extends AbstractDaemonService {

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    @Autowired
    public RfidService(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        super(INTERVAL);
        this.callback = callback;
        this.rfidOperator = rfidOperator;
    }

    @Override
    public void run() {
        rfidOperator.connect(callback);

        rfidOperator.startRead();

        super.setStartup(true);
    }

}
