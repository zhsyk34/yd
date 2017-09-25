package com.yd.estore.rfid;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.estore.config.StoreConfig;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.estore.config.RfidConfig.RFID_ID;

@Service
public class RfidService extends AbstractDaemonService {

    private final IAsynchronousMessage callback;

    @Getter
    private final RfidOperator rfidOperator;

    @Autowired
    public RfidService(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        super(StoreConfig.STORE_INTERVAL);
        this.callback = callback;
        this.rfidOperator = rfidOperator;
    }

    @Override
    public void run() {
        rfidOperator.connect(callback);

        CLReader.Stop(RFID_ID);

        rfidOperator.startRead();

        super.setStartup(true);
    }

}