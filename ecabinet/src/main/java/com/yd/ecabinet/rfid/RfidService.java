package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RfidService extends AbstractDaemonService {

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    @Override
    public void run() {
        rfidOperator.connect(callback);
        super.setFinished(true);
    }

}
