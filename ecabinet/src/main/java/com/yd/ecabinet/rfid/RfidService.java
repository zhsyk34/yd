package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RfidService extends AbstractDaemonService {
    private final IAsynchronousMessage callback;
    private final RfidOperator rfidOperator;

    private final ConnectInitializing connectInitializing;

    @Override
    public void run() {
        rfidOperator.connect(callback);

        connectInitializing.afterConnect();

        super.setFinished(true);
    }
}
