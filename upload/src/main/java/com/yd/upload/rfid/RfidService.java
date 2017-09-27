package com.yd.upload.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.yd.upload.config.StoreConfig.INTERVAL;
import static com.yd.upload.config.StoreConfig.MAC;

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

        this.initMAC();

        rfidOperator.startRead();

        super.setStartup(true);
    }

    private void initMAC() {
        String mac = rfidOperator.getMAC();
        if (StringUtils.hasText(mac)) {
            MAC = mac.replaceAll("[-:]", "");
        }
    }

}
